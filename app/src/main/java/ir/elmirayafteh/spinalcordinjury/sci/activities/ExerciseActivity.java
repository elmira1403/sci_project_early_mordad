package ir.elmirayafteh.spinalcordinjury.sci.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import ir.elmirayafteh.spinalcordinjury.sci.Exercise;
import ir.elmirayafteh.spinalcordinjury.sci.ExerciseAdapter;
import ir.elmirayafteh.spinalcordinjury.sci.R;
import ir.elmirayafteh.spinalcordinjury.sci.database.RecCaseDbSchema;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ExerciseActivity extends AppCompatActivity {
    public SQLiteDatabase mDatabase;
    RecyclerView mRecyclerView;
    ExerciseAdapter aAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<Exercise> data = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = openOrCreateDatabase("recCaseBase.db", MODE_PRIVATE, null);
        aAdapter = new ExerciseAdapter(this, data);
        mRecyclerView.setAdapter(aAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        getContents();
    }

    public static ContentValues getContentValues (Exercise mExercise){
        ContentValues values = new ContentValues();
        values.put(RecCaseDbSchema.ExerciseTable.Cols.SUBJECT, mExercise.getGoal());
        values.put(RecCaseDbSchema.ExerciseTable.Cols.IMAGE, mExercise.getImage_file());
        values.put(RecCaseDbSchema.ExerciseTable.Cols.DESC, mExercise.getDesc());
        return values;
    }

    public void getContents(){
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM Exercise", null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            String sub =cursor.getString(cursor.getColumnIndex(RecCaseDbSchema.ExerciseTable.Cols.SUBJECT));
            String img =cursor.getString(cursor.getColumnIndex(RecCaseDbSchema.ExerciseTable.Cols.IMAGE));
            String des =cursor.getString(cursor.getColumnIndex(RecCaseDbSchema.ExerciseTable.Cols.DESC));

            Exercise temp= new Exercise(sub, img, des);
            aAdapter.inflateList(temp);
            cursor.moveToNext();
        }

        cursor.close();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(ir.elmirayafteh.spinalcordinjury.sci.R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_help) {
            Intent i = new Intent(ExerciseActivity.this, HelpActivity.class);
            i.putExtra("caller", "exercise_help");
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
