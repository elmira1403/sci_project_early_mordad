package ir.elmirayafteh.spinalcordinjury.sci.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import ir.elmirayafteh.spinalcordinjury.sci.CustomList;
import ir.elmirayafteh.spinalcordinjury.sci.DataBaseCheck;
import ir.elmirayafteh.spinalcordinjury.sci.MyPreferences;
import ir.elmirayafteh.spinalcordinjury.sci.R;
import ir.elmirayafteh.spinalcordinjury.sci.User;
import ir.elmirayafteh.spinalcordinjury.sci.UserInfo;
import ir.elmirayafteh.spinalcordinjury.sci.database.RecCaseBaseHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {
    public SharedPreferences pref;
    public final String USER = "MyUser";
    public SQLiteDatabase mDatabase;
    String phone_number;
    String password;
    private final static int WRITE_EXTERNAL_RESULT = 100;
    private ProgressDialog progress;
    public ProgressDialog dl_progress;
    ListView homeListView;
    Retrofit retrofit;

    Boolean askedBefore;
    Boolean gotPermission = false;

    private Integer imageId[] = {
            R.drawable.user,
            R.drawable.menu_play,
            R.drawable.wheelchair,
            R.drawable.star_filled,
            R.drawable.tutorial
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);

        String[] homeStringArray = getResources().getStringArray(ir.elmirayafteh.spinalcordinjury.sci.R.array.homeList);
        CustomList customList = new CustomList(this, homeStringArray, imageId);

        homeListView = (ListView) findViewById(R.id.listView);
        homeListView.setAdapter(customList);


        pref = getSharedPreferences(USER, MODE_PRIVATE);
        phone_number = pref.getString("phone_number", null);
        password = pref.getString("password", null);

//        if (!pref.contains("asked_before")) {
//            askedBefore = false;
//            gotPermission = false;
//        } else {
//            if (pref.getString("asked_before", null) == null) {
//                askedBefore = false;
//                gotPermission = false;
//            } else {
//                askedBefore = Boolean.parseBoolean(pref.getString("asked_before", null));
//                gotPermission = Boolean.parseBoolean(pref.getString("got_permission", null));
//            }
//        }

        retrofit = new Retrofit.Builder()
                .baseUrl("http://elmirayafteh.ir/sciwebservice/").addConverterFactory(GsonConverterFactory.create())
                .build();

        User uService = retrofit.create(User.class);

        uService.getUserInfo(phone_number, password).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    UserInfo mUserInfo = response.body();

                    MyPreferences myPreferences = new MyPreferences(pref);
                    pref = myPreferences.saveOnDevice(mUserInfo);

                    askForPermission(gotPermission);

                    DataBaseCheck dataBaseCheck = new DataBaseCheck();
                    dataBaseCheck.dataBaseChecker(pref, MainActivity.this);


                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error in Getting User Info", Toast.LENGTH_LONG).show();
            }
        });


        homeListView.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i;
                switch (position) {
                    case 0:
                        i = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(i);
                        return;
                    case 1:
                        i = new Intent(MainActivity.this, RecommendationActivity.class);
                        startActivity(i);
                        return;
                    case 2:
                        i = new Intent(MainActivity.this, ExerciseActivity.class);
                        startActivity(i);
                        return;
                    case 3:
                        i = new Intent(MainActivity.this, FavoritesActivity.class);
                        startActivity(i);
                        return;
                    case 4:
                        i = new Intent(MainActivity.this, TutorialActivity.class);
                        i.putExtra("caller", "main");
                        startActivity(i);
                        return;
                }
            }
        });

    }


    public void askForPermission(Boolean gotPermission) {

        if (!gotPermission) {
            String[] perm = new String[1];
            perm[0] = WRITE_EXTERNAL_STORAGE;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                requestPermissions(perm, WRITE_EXTERNAL_RESULT);
            }

            gotPermission = true;
        }

        SharedPreferences.Editor editor = pref.edit();
        editor.putString("asked_before", "true");
        editor.putString("got_permission", gotPermission.toString());
        editor.apply();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(ir.elmirayafteh.spinalcordinjury.sci.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_help) {
            Intent i = new Intent(MainActivity.this, HelpActivity.class);
            i.putExtra("caller", "main_help");
            startActivity(i);
        }

        if (id == R.id.action_log_out) {
            pref = getSharedPreferences(USER, MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.apply();

            mDatabase = new RecCaseBaseHelper(MainActivity.this).getWritableDatabase();
            mDatabase.execSQL("delete from Solution");
            mDatabase.execSQL("delete from Exercise");
            mDatabase.execSQL("delete from Favorites");
            mDatabase.execSQL("delete from FavExercise");
            mDatabase.close();

            Intent i = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}