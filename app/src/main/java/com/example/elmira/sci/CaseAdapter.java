package com.example.elmira.sci;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

public class CaseAdapter extends RecyclerView.Adapter<CaseViewHolder> {
    public Context mContext;
    public List<Case> mData;

    public CaseAdapter(Context context, List<Case> data) {
        mContext = context;
        mData = data;
    }

    public void whoKnows(Case s) {
        mData.add(s);
        notifyDataSetChanged();
    }


    public void showErrorMessage() {
        Toast.makeText(mContext, "Error Here in Case", Toast.LENGTH_LONG).show();
    }


    @Override
    public CaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        return new CaseViewHolder(mContext, v);
    }

    @Override
    public void onBindViewHolder(CaseViewHolder holder, int position) {
        holder.bindData(mContext, mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
