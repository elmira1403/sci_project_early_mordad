package ir.elmirayafteh.spinalcordinjury.sci.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecCaseBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "recCaseBase.db";

    public RecCaseBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + RecCaseDbSchema.SolutionTable.NAME+ "(" +
                " _id integer primary key autoincrement, " +
                RecCaseDbSchema.SolutionTable.Cols.SUBJECT + " not null unique, " +
                RecCaseDbSchema.SolutionTable.Cols.VIDEO + ", " +
                RecCaseDbSchema.SolutionTable.Cols.VOICE + ", " +
                RecCaseDbSchema.SolutionTable.Cols.TEXT + ", " +
                RecCaseDbSchema.SolutionTable.Cols.DESC +
                ")");

        db.execSQL("create table " + RecCaseDbSchema.FavoritesTable.NAME+ "(" +
                " _id integer primary key autoincrement, " +
                RecCaseDbSchema.FavoritesTable.Cols.F_SUBJECT + ", " +
                RecCaseDbSchema.FavoritesTable.Cols.F_VIDEO + ", " +
                RecCaseDbSchema.FavoritesTable.Cols.F_VOICE + ", " +
                RecCaseDbSchema.FavoritesTable.Cols.F_TEXT + ", " +
                RecCaseDbSchema.FavoritesTable.Cols.F_DESC +
                ")");

        db.execSQL("create table " + RecCaseDbSchema.ExerciseTable.NAME+ "(" +
                " _id integer primary key autoincrement, " +
                RecCaseDbSchema.ExerciseTable.Cols.SUBJECT + " not null unique, " +
                RecCaseDbSchema.ExerciseTable.Cols.IMAGE + ", " +
                RecCaseDbSchema.ExerciseTable.Cols.DESC +
                ")");

        db.execSQL("create table " + RecCaseDbSchema.FavExerciseTable.NAME+ "(" +
                " _id integer primary key autoincrement, " +
                RecCaseDbSchema.ExerciseTable.Cols.SUBJECT + " not null unique, " +
                RecCaseDbSchema.ExerciseTable.Cols.IMAGE + ", " +
                RecCaseDbSchema.ExerciseTable.Cols.DESC +
                ")");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}