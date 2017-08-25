package ir.elmirayafteh.spinalcordinjury.sci;


import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import ir.elmirayafteh.spinalcordinjury.sci.activities.ExerciseActivity;
import ir.elmirayafteh.spinalcordinjury.sci.activities.RecommendationActivity;
import ir.elmirayafteh.spinalcordinjury.sci.database.RecCaseBaseHelper;
import ir.elmirayafteh.spinalcordinjury.sci.database.RecCaseDbSchema;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ir.elmirayafteh.spinalcordinjury.sci.database.RecCaseDbSchema.ExerciseTable.Cols.IMAGE;
import static ir.elmirayafteh.spinalcordinjury.sci.database.RecCaseDbSchema.SolutionTable.Cols.VIDEO;
import static ir.elmirayafteh.spinalcordinjury.sci.database.RecCaseDbSchema.SolutionTable.Cols.VOICE;

public class DataBaseCheck {
    private Context mContext;
    private SharedPreferences pref;
    private final String SCI_TYPE = "sci_type";
    private final String XP_LEVEL = "experience_level";
    private final String FITNESS = "fitness";
    private final String HEIGHT = "height";
    private final String UP_STRENGTH = "upper_strength";
    private final String GENDER = "gender";
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
    }

    private void databaseInflater() {

        String sci_type = pref.getString(SCI_TYPE, null);
        String experience_level = pref.getString(XP_LEVEL, null);
        String fitness = pref.getString(FITNESS, null);
        String height = pref.getString(HEIGHT, null);
        String upper_strength = pref.getString(UP_STRENGTH, null);
        String gender = pref.getString(GENDER, null);

        RCList service = retrofit.create(RCList.class);

        service.getCases(sci_type, experience_level, fitness, height, upper_strength, gender).enqueue(new Callback<List<Case>>() {

            @Override
            public void onResponse(Call<List<Case>> call, Response<List<Case>> response) {
                if (response.isSuccessful()) {
                    mDatabase = new RecCaseBaseHelper(mContext).getWritableDatabase();

                    for (int i = 0; i < response.body().size(); i++) {
                        ContentValues values = RecommendationActivity.getContentValues(response.body().get(i));
                        mDatabase.insert(RecCaseDbSchema.SolutionTable.NAME, null, values);
                    }
                    mDatabase.close();
                    mediaFiles();
                }
            }

            @Override
            public void onFailure(Call<List<Case>> call, Throwable t) {
                Toast.makeText(mContext, "Unable to retrieve case list", Toast.LENGTH_LONG).show();
            }
        });

        ExerciseList e_service = retrofit.create(ExerciseList.class);
        e_service.getCases(sci_type, experience_level, upper_strength).enqueue(new Callback<List<Exercise>>() {

            @Override
            public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
                if (response.isSuccessful()) {
                    mDatabase = new RecCaseBaseHelper(mContext).getWritableDatabase();

                    for (int i = 0; i < response.body().size(); i++) {
                        ContentValues values = ExerciseActivity.getContentValues(response.body().get(i));
                        mDatabase.insert(RecCaseDbSchema.ExerciseTable.NAME, null, values);
                    }
                    mDatabase.close();
                    getImageFiles();
                }
            }

            @Override
            public void onFailure(Call<List<Exercise>> call, Throwable t) {
                Toast.makeText(mContext, "Unable to retrieve exercises list", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void mediaFiles() {

        SQLiteDatabase mDatabase = new RecCaseBaseHelper(mContext).getWritableDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT video_file, voice_file FROM Solution", null);
        cursor.moveToFirst();

        File f = new File(Environment.getExternalStorageDirectory() + "/sci/videos");
        File[] contents = f.listFiles();

        File f_voice = new File(Environment.getExternalStorageDirectory() + "/sci/voices");
        File[] contents_voice = f_voice.listFiles();

        Boolean check;
        Boolean voice_check;

        while (!cursor.isAfterLast()) {
            String vid = cursor.getString(cursor.getColumnIndex(VIDEO));
            String voi = cursor.getString(cursor.getColumnIndex(VOICE));

            check = true;
            if (contents != null) {
                for (File content : contents) {
                    if (content.getName().equals(vid)) {
                        check = false;
                    }

                }
            }

            voice_check = true;
            if (contents_voice != null) {
                for (File content : contents_voice) {
                    if (content.getName().equals(voi)) {
                        voice_check = false;
                    }

                }
            }

            if (vid != null && !vid.equals("") && check) {
                downloadFile("videos", vid);
            }

            if (voi != null && !voi.equals("") && voice_check) {
                downloadFile("voices", voi);
            }

            cursor.moveToNext();

        }
        cursor.close();
        mDatabase.close();

    }

    private void getImageFiles() {

        SQLiteDatabase mDatabase = new RecCaseBaseHelper(mContext).getWritableDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT image_file FROM Exercise", null);
        cursor.moveToFirst();

        File f = new File(Environment.getExternalStorageDirectory() + "/sci/images");
        File[] contents = f.listFiles();

        Boolean check;

        while (!cursor.isAfterLast()) {
            String img = cursor.getString(cursor.getColumnIndex(IMAGE));

            check = true;
            if (contents != null) {
                for (File content : contents) {
                    if (content.getName().equals(img)) {
                        check = false;
                    }

                }
            }

            if (img != null && !img.equals("") && check) {
                downloadFile("images", img);
            }

            cursor.moveToNext();

        }
        cursor.close();
        mDatabase.close();
    }

    private void downloadFile(String folder, String fileName) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(DownloadUrl + folder + "/" + fileName));
        request.allowScanningByMediaScanner();
        request.setDestinationInExternalPublicDir("sci/" + folder + "/", fileName);
        DownloadManager manager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);

        Boolean check = true;
        File f = new File(Environment.getExternalStorageDirectory() + "/sci/" + folder);
        File[] contents = f.listFiles();
        if (contents != null) {
            for (File content : contents) {
                if (content.getName().equals(fileName)) {
                    check = false;
                }
            }
        }

        if (check) {
            manager.enqueue(request);
        }

    }
}
