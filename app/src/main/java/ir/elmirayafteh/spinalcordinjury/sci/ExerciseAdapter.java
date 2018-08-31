package ir.elmirayafteh.spinalcordinjury.sci;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseViewHolder> {
    private Context mContext;
    private List<Exercise> mData;

    public ExerciseAdapter(Context context, List<Exercise> data) {
        mContext = context;
        mData = data;
    }

    public void inflateList(Exercise s) {
        mData.add(s);
        notifyDataSetChanged();
    }



    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        return new ExerciseViewHolder(mContext, v);
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
