package ir.elmirayafteh.spinalcordinjury.sci.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ir.elmirayafteh.spinalcordinjury.sci.Exercise;
import ir.elmirayafteh.spinalcordinjury.sci.ExerciseAdapter;
import ir.elmirayafteh.spinalcordinjury.sci.R;
import ir.elmirayafteh.spinalcordinjury.sci.database.RecCaseBaseHelper;
import ir.elmirayafteh.spinalcordinjury.sci.database.RecCaseDbSchema;

public class FavExerFragment extends Fragment {

    public SQLiteDatabase mDatabase;
    RecyclerView mRecyclerView;
    ExerciseAdapter cAdapter;
    ArrayList<Exercise> eData;
    View rootView;

    public FavExerFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_two, container, false);

        eData = new ArrayList<>();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.fragmentRecycleView2);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mDatabase = new RecCaseBaseHelper(getActivity()).getWritableDatabase();
        cAdapter = new ExerciseAdapter(getContext(), eData);
        mRecyclerView.setAdapter(cAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        getContents();

        return rootView;
    }

    public void getContents() {

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM FavExercise", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String sub = cursor.getString(cursor.getColumnIndex(RecCaseDbSchema.FavExerciseTable.Cols.SUBJECT));
            String img = cursor.getString(cursor.getColumnIndex(RecCaseDbSchema.FavExerciseTable.Cols.IMAGE));
            String des = cursor.getString(cursor.getColumnIndex(RecCaseDbSchema.FavExerciseTable.Cols.DESC));

            Exercise temp = new Exercise(sub, img, des);
            cAdapter.inflateList(temp);
            cursor.moveToNext();
        }

        cursor.close();

    }


}
