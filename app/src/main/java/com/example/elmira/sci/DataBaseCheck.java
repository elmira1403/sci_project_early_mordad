package com.example.elmira.sci;


import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.example.elmira.sci.activities.RecommendationActivity;
import com.example.elmira.sci.database.RecCaseBaseHelper;
import com.example.elmira.sci.database.RecCaseDbSchema;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.elmira.sci.database.RecCaseDbSchema.SolutionTable.Cols.VIDEO;
import static com.example.elmira.sci.database.RecCaseDbSchema.SolutionTable.Cols.VOICE;

public class DataBaseCheck {
    private Context mContext;
    private SharedPreferences pref;
    private final String SCI_TYPE = "sci_type";
    private final String XP_LEVEL = "experience_level";
    private final String FITNESS = "fitness";
    private final String HEIGHT = "height";
    private final String UP_STRENGTH = "upper_strength";
    private final String SEX = "sex";
    private SQLiteDatabase mDatabase;
    private String DownloadUrl = "http://elmirayafteh.ir/sciwebservice/";

    private Retrofit retrofit;

    public void dataBaseChecker(SharedPreferences shPref, Context m_context) {
        pref = shPref;
        mContext = m_context;
        retrofit = new Retrofit.Builder()
                .baseUrl("http://elmirayafteh.ir/sciwebservice/").addConverterFactory(GsonConverterFactory.create())
                .build();

        databaseInflater();
        mediaFiles();

    }

    private void databaseInflater() {

        String sci_type = pref.getString(SCI_TYPE, null);
        String experience_level = pref.getString(XP_LEVEL, null);
        String fitness = pref.getString(FITNESS, null);
        String height = pref.getString(HEIGHT, null);
        String upper_strength = pref.getString(UP_STRENGTH, null);
        String sex = pref.getString(SEX, null);

        RCList service = retrofit.create(RCList.class);

        service.getCases(sci_type, experience_level, fitness, height, upper_strength, sex).enqueue(new Callback<List<Case>>() {

            @Override
            public void onResponse(Call<List<Case>> call, Response<List<Case>> response) {
                if (response.isSuccessful()) {
                    mDatabase = new RecCaseBaseHelper(mContext).getWritableDatabase();
//                    mDatabase.execSQL("delete from Solution");

                    for (int i = 0; i < response.body().size(); i++) {
                        ContentValues values = RecommendationActivity.getContentValues(response.body().get(i));
                        mDatabase.insert(RecCaseDbSchema.SolutionTable.NAME, null, values);
                    }
                    mDatabase.close();
                }
            }

            @Override
            public void onFailure(Call<List<Case>> call, Throwable t) {
                Toast.makeText(mContext, "Unable to retrieve the list", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void mediaFiles() {

        SQLiteDatabase mDatabase = new RecCaseBaseHelper(mContext).getWritableDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT video_file, voice_file FROM Solution", null);
        cursor.moveToFirst();

        File f = new File(Environment.getExternalStorageDirectory() + "/sci/videos");
        File[] contents = f.listFiles();

        Boolean check;

        while (!cursor.isAfterLast()) {
            String vid = cursor.getString(cursor.getColumnIndex(VIDEO));
            String voi = cursor.getString(cursor.getColumnIndex(VOICE));

            check = true;
            for (File content : contents) {
                if (content.getName().equals(vid)) {
                    check = false;
                }
            }

            if (!vid.equals("") && check) {
                downloadFile("videos", vid);
            }

            if (!voi.equals("")) {
                downloadFile("voices", voi);
            }

            cursor.moveToNext();
        }
        cursor.close();
        mDatabase.close();
    }

    private void downloadFile(String folder, String fileName) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(DownloadUrl + folder + "/" + fileName));
        request.allowScanningByMediaScanner();
        request.setDestinationInExternalPublicDir("sci/videos", fileName);
        DownloadManager manager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

    }
}
