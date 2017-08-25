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

import ir.elmirayafteh.spinalcordinjury.sci.Case;
import ir.elmirayafteh.spinalcordinjury.sci.CaseAdapter;
import ir.elmirayafteh.spinalcordinjury.sci.R;
import ir.elmirayafteh.spinalcordinjury.sci.database.RecCaseDbSchema.SolutionTable;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static ir.elmirayafteh.spinalcordinjury.sci.database.RecCaseDbSchema.SolutionTable.Cols.DESC;
import static ir.elmirayafteh.spinalcordinjury.sci.database.RecCaseDbSchema.SolutionTable.Cols.SUBJECT;
import static ir.elmirayafteh.spinalcordinjury.sci.database.RecCaseDbSchema.SolutionTable.Cols.TEXT;
import static ir.elmirayafteh.spinalcordinjury.sci.database.RecCaseDbSchema.SolutionTable.Cols.VIDEO;
import static ir.elmirayafteh.spinalcordinjury.sci.database.RecCaseDbSchema.SolutionTable.Cols.VOICE;

public class RecommendationActivity extends AppCompatActivity {
    public SQLiteDatabase mDatabase;
    RecyclerView mRecyclerView;
    CaseAdapter aAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<Case> data = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = openOrCreateDatabase("recCaseBase.db", MODE_PRIVATE, null);
        aAdapter = new CaseAdapter(this, data);
        mRecyclerView.setAdapter(aAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        getContents();
    }

    public static ContentValues getContentValues (Case mCase){
        ContentValues values = new ContentValues();
        values.put(SolutionTable.Cols.SUBJECT, mCase.getSubject());
        values.put(SolutionTable.Cols.VIDEO, mCase.getVideo_file());
        values.put(SolutionTable.Cols.VOICE, mCase.getVoice_file());
        values.put(SolutionTable.Cols.TEXT, mCase.getText_file());
        values.put(SolutionTable.Cols.DESC, mCase.getDesc());
        return values;
    }

    public void getContents(){
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM Solution", null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            String sub =cursor.getString(cursor.getColumnIndex(SUBJECT));
            String vid =cursor.getString(cursor.getColumnIndex(VIDEO));
            String voi =cursor.getString(cursor.getColumnIndex(VOICE));
            String txt =cursor.getString(cursor.getColumnIndex(TEXT));
            String des =cursor.getString(cursor.getColumnIndex(DESC));

            Case temp= new Case(sub,vid,voi,txt,des);
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
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_help) {
            Intent i = new Intent(RecommendationActivity.this, HelpActivity.class);
            i.putExtra("caller", "recommend_help");
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
