package com.example.elmira.sci.activities;

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

import com.example.elmira.sci.Case;
import com.example.elmira.sci.CaseAdapter;
import com.example.elmira.sci.R;
import com.example.elmira.sci.database.RecCaseDbSchema;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.example.elmira.sci.database.RecCaseDbSchema.FavoritesTable.Cols.F_DESC;
import static com.example.elmira.sci.database.RecCaseDbSchema.FavoritesTable.Cols.F_SUBJECT;
import static com.example.elmira.sci.database.RecCaseDbSchema.FavoritesTable.Cols.F_TEXT;
import static com.example.elmira.sci.database.RecCaseDbSchema.FavoritesTable.Cols.F_VIDEO;
import static com.example.elmira.sci.database.RecCaseDbSchema.FavoritesTable.Cols.F_VOICE;

public class FavoritesActivity extends AppCompatActivity {
    public SQLiteDatabase mDatabase;
    RecyclerView mRecyclerView;
    CaseAdapter bAdapter;
    ArrayList<Case> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        data = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycView2);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = openOrCreateDatabase("recCaseBase.db", MODE_PRIVATE, null);
        bAdapter = new CaseAdapter(this, data);
        mRecyclerView.setAdapter(bAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        getContents();

    }

    public static ContentValues getContentValues (Case mCase){
        ContentValues values = new ContentValues();
        values.put(RecCaseDbSchema.FavoritesTable.Cols.F_SUBJECT, mCase.getSubject());
        values.put(RecCaseDbSchema.FavoritesTable.Cols.F_VIDEO, mCase.getVideo_file());
        values.put(RecCaseDbSchema.FavoritesTable.Cols.F_VOICE, mCase.getVoice_file());
        values.put(RecCaseDbSchema.FavoritesTable.Cols.F_TEXT, mCase.getText_file());
        values.put(RecCaseDbSchema.FavoritesTable.Cols.F_DESC, mCase.getDesc());
        return values;
    }

    public void getContents(){
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM Favorites", null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            String sub =cursor.getString(cursor.getColumnIndex(F_SUBJECT));
            String vid =cursor.getString(cursor.getColumnIndex(F_VIDEO));
            String voi =cursor.getString(cursor.getColumnIndex(F_VOICE));
            String txt =cursor.getString(cursor.getColumnIndex(F_TEXT));
            String des =cursor.getString(cursor.getColumnIndex(F_DESC));

            Case temp= new Case(sub,vid,voi,txt,des);
            bAdapter.inflateList(temp);
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
            Intent i = new Intent(FavoritesActivity.this, HelpActivity.class);
            i.putExtra("caller", "fav_help");
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume(){
        super.onResume();

        data.clear();
        getContents();
    }

}
