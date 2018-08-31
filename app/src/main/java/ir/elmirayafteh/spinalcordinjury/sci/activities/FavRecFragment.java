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

import ir.elmirayafteh.spinalcordinjury.sci.Case;
import ir.elmirayafteh.spinalcordinjury.sci.CaseAdapter;
import ir.elmirayafteh.spinalcordinjury.sci.R;
import ir.elmirayafteh.spinalcordinjury.sci.database.RecCaseBaseHelper;
import ir.elmirayafteh.spinalcordinjury.sci.database.RecCaseDbSchema;

public class FavRecFragment extends Fragment {

    public SQLiteDatabase mDatabase;
    RecyclerView mRecyclerView;
    CaseAdapter bAdapter;
    ArrayList<Case> data;
    View rootView;

    public FavRecFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_one, container, false);

        data = new ArrayList<>();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.fragmentRecycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mDatabase = new RecCaseBaseHelper(getActivity()).getWritableDatabase();
        bAdapter = new CaseAdapter(getContext(), data);
        mRecyclerView.setAdapter(bAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        getContents();

        return rootView;
    }

    public void getContents() {
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM Favorites", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String sub = cursor.getString(cursor.getColumnIndex(RecCaseDbSchema.FavoritesTable.Cols.F_SUBJECT));
            String vid = cursor.getString(cursor.getColumnIndex(RecCaseDbSchema.FavoritesTable.Cols.F_VIDEO));
            String voi = cursor.getString(cursor.getColumnIndex(RecCaseDbSchema.FavoritesTable.Cols.F_VOICE));
            String txt = cursor.getString(cursor.getColumnIndex(RecCaseDbSchema.FavoritesTable.Cols.F_TEXT));
            String des = cursor.getString(cursor.getColumnIndex(RecCaseDbSchema.FavoritesTable.Cols.F_DESC));

            Case temp = new Case(sub, vid, voi, txt, des);
            bAdapter.inflateList(temp);
            cursor.moveToNext();
        }

        cursor.close();

    }
}
