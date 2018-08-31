package ir.elmirayafteh.spinalcordinjury.sci.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

import ir.elmirayafteh.spinalcordinjury.sci.Exercise;
import ir.elmirayafteh.spinalcordinjury.sci.R;
import ir.elmirayafteh.spinalcordinjury.sci.database.RecCaseBaseHelper;
import ir.elmirayafteh.spinalcordinjury.sci.database.RecCaseDbSchema;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ShowExerciseActivity extends AppCompatActivity {

    private String EMPTY = "empty";
    private String FILLED = "filled";
    private String DB_NAME = "recCaseBase.db";
    private boolean star = false;
    public String goal;
    public String desc;
    public String url;
    public SQLiteDatabase mDatabase;
    FragmentManager fm;
    Fragment fragment;
    Toolbar toolbar;
    ImageView imageView;
    TextView descTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_exercise);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        goal = intent.getStringExtra("goal");
        desc = intent.getStringExtra("desc");
        url = intent.getStringExtra("image_link");
        setTitle(goal);

        imageView = (ImageView) findViewById(R.id.imageView);
        descTextView = (TextView) findViewById(R.id.descTextView);

        File file = new File(Environment.getExternalStorageDirectory() + "/sci/images/" + url);
        Picasso.get().load(file).into(imageView);

        if (!desc.equals("")) {
            descTextView.setVisibility(View.VISIBLE);
            descTextView.setText(desc);
        }

//        fm = getSupportFragmentManager();
//        fragment = fm.findFragmentById(R.id.imageFragment);
//
//        if (fragment == null) {
//            fragment = new ImageFragment();
//            Bundle args = new Bundle();
//            args.putString("image_link", url);
//            fragment.setArguments(args);
//            fm.beginTransaction()
//                    .add(R.id.imageFragment, fragment)
//                    .commit();
//        }


        mDatabase = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM FavExercise", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String sub = cursor.getString(cursor.getColumnIndex(RecCaseDbSchema.FavExerciseTable.Cols.SUBJECT));

            if (sub.equals(goal)) {
                star = true;
            }
            cursor.moveToNext();
        }
        cursor.close();
        mDatabase.close();

    }


    public void removeSingleRow(String title) {
        mDatabase = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        mDatabase.execSQL("DELETE FROM FavExercise WHERE " + RecCaseDbSchema.FavExerciseTable.Cols.SUBJECT + "= '" + title + "'");
        mDatabase.close();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_case, menu);
        return true;
    }

    public void starClick(MenuItem item) {

        if (item.getTitle() == EMPTY) {
            item.setIcon(getResources().getDrawable(R.drawable.star_filled));
            item.setTitle(FILLED);

            Exercise mExercise = new Exercise(goal, url, desc);
            mDatabase = new RecCaseBaseHelper(ShowExerciseActivity.this).getWritableDatabase();
            ContentValues values = FavoritesActivity.getContentValues(mExercise);
            mDatabase.insert(RecCaseDbSchema.FavExerciseTable.NAME, null, values);
            mDatabase.close();

        } else {
            item.setIcon(getResources().getDrawable(R.drawable.star_empty));
            item.setTitle(EMPTY);
            removeSingleRow(goal);
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's instance
//        getSupportFragmentManager().putFragment(outState, "fragment", fragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_help) {
            Intent i = new Intent(ShowExerciseActivity.this, HelpActivity.class);
            i.putExtra("caller", "show_exercise_help");
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
}
