package com.example.elmira.sci.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.elmira.sci.database.RecCaseDbSchema.SolutionTable;

public class RecCaseBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "recCaseBase.db";

    public RecCaseBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + SolutionTable.NAME+ "(" +
                " _id integer primary key autoincrement, " +
                SolutionTable.Cols.SUBJECT + " not null unique, " +
                SolutionTable.Cols.VIDEO + ", " +
                SolutionTable.Cols.VOICE + ", " +
                SolutionTable.Cols.TEXT + ", " +
                SolutionTable.Cols.DESC +
                ")");

        db.execSQL("create table " + RecCaseDbSchema.FavoritesTable.NAME+ "(" +
                " _id integer primary key autoincrement, " +
                SolutionTable.Cols.SUBJECT + ", " +
                SolutionTable.Cols.VIDEO + ", " +
                SolutionTable.Cols.VOICE + ", " +
                SolutionTable.Cols.TEXT + ", " +
                SolutionTable.Cols.DESC +
                ")");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}