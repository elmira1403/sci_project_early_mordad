package ir.elmirayafteh.spinalcordinjury.sci.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import ir.elmirayafteh.spinalcordinjury.sci.Case;
import ir.elmirayafteh.spinalcordinjury.sci.R;
import ir.elmirayafteh.spinalcordinjury.sci.database.RecCaseBaseHelper;
import ir.elmirayafteh.spinalcordinjury.sci.database.RecCaseDbSchema;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static ir.elmirayafteh.spinalcordinjury.sci.activities.VoicePlayerFragment.mediaPlayer;

public class ShowCaseActivity extends AppCompatActivity {

    private String EMPTY = "empty";
    private String FILLED = "filled";
    private String DB_NAME = "recCaseBase.db";
    private boolean star = false;
    public String subject;
    public String text;
    public String desc;
    public String url;
    public String v_url;
    public SQLiteDatabase mDatabase;
    FragmentManager vc_fm;
    Fragment vc_fragment;
    FragmentManager fm;
    Fragment fragment;
    Toolbar toolbar;
    TextView descTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_case);

        Intent intent = getIntent();
        subject = intent.getStringExtra("case_subject");
        text = intent.getStringExtra("text");
        desc = intent.getStringExtra("desc");
        url = intent.getStringExtra("voice_link");
        v_url = intent.getStringExtra("video_link");

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        descTextView = (TextView) findViewById(R.id.descTextView);

//        if (savedInstanceState != null) {
//            //Restore the fragment's instance
//             fragment = getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
//        }


        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(subject);


            if (!text.equals("")) {
                TextView textTextView = (TextView) findViewById(R.id.textTextView);
                textTextView.setVisibility(View.VISIBLE);
                textTextView.setText(text);
            }

        if (!v_url.equals("")) {
            if (!desc.equals("")) {
                descTextView.setVisibility(View.VISIBLE);
                descTextView.setText(desc);
            }

            fm = getSupportFragmentManager();
            fragment = fm.findFragmentById(R.id.videoFragment);

            if (fragment == null) {
                fragment = new VideoPlayerFragment();
                Bundle args = new Bundle();
                args.putString("video_link", v_url);
                fragment.setArguments(args);
                fm.beginTransaction()
                        .add(R.id.videoFragment, fragment)
                        .commit();
            }
        }


        if (!url.equals("")) {
            vc_fm = getSupportFragmentManager();
            vc_fragment = vc_fm.findFragmentById(R.id.voiceFragment);

            if (vc_fragment == null) {
                vc_fragment = new VoicePlayerFragment();
                Bundle args = new Bundle();
                args.putString("voice_link", url);
                vc_fragment.setArguments(args);
                vc_fm.beginTransaction()
                        .add(R.id.voiceFragment, vc_fragment)
                        .commit();
            }
        }
    } else if(this.getResources().getConfiguration().orientation ==Configuration.ORIENTATION_LANDSCAPE) {

        if (!v_url.equals("")) {
            fm = getSupportFragmentManager();
            fragment = fm.findFragmentById(R.id.videoFragment);

            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

            if (fragment == null) {
                fragment = new VideoPlayerFragment();
                Bundle args = new Bundle();
                args.putString("video_link", v_url);
                fragment.setArguments(args);
                fm.beginTransaction()
                        .add(R.id.videoFragment, fragment)
                        .commit();
            }

        }
    }


    mDatabase =

    openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);

    Cursor cursor = mDatabase.rawQuery("SELECT * FROM Favorites", null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast())

    {
        String sub = cursor.getString(cursor.getColumnIndex(RecCaseDbSchema.FavoritesTable.Cols.F_SUBJECT));

        if (sub.equals(subject)) {
            star = true;
        }
        cursor.moveToNext();
    }
        cursor.close();
        mDatabase.close();

}


    public void removeSingleRow(String title) {
        mDatabase = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        mDatabase.execSQL("DELETE FROM Favorites WHERE " + RecCaseDbSchema.FavoritesTable.Cols.F_SUBJECT + "= '" + title + "'");
        mDatabase.close();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(ir.elmirayafteh.spinalcordinjury.sci.R.menu.menu_show_case, menu);
        return true;
    }

    public void starClick(MenuItem item) {

        if (item.getTitle() == EMPTY) {
            item.setIcon(getResources().getDrawable(R.drawable.star_filled));
            item.setTitle(FILLED);

            Case mCase = new Case(subject, v_url, url, text, desc);
            mDatabase = new RecCaseBaseHelper(ShowCaseActivity.this).getWritableDatabase();
            ContentValues values = FavoritesActivity.getContentValues(mCase);
            mDatabase.insert(RecCaseDbSchema.FavoritesTable.NAME, null, values);
            mDatabase.close();

        } else {
            item.setIcon(getResources().getDrawable(R.drawable.star_empty));
            item.setTitle(EMPTY);
            removeSingleRow(subject);
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's instance
        getSupportFragmentManager().putFragment(outState, "fragment", fragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_help) {
            Intent i = new Intent(ShowCaseActivity.this, HelpActivity.class);
            i.putExtra("caller", "show_case_help");
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem starMenuItem = menu.findItem(R.id.action_star);
        if (star) {
            starMenuItem.setTitle(FILLED);
            starMenuItem.setIcon(getResources().getDrawable(R.drawable.star_filled));
        } else {
            starMenuItem.setTitle(EMPTY);
            starMenuItem.setIcon(getResources().getDrawable(R.drawable.star_empty));

        }

        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
