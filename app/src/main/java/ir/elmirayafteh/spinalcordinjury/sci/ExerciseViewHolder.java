package ir.elmirayafteh.spinalcordinjury.sci;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.elmirayafteh.spinalcordinjury.sci.activities.ShowExerciseActivity;

public class ExerciseViewHolder extends RecyclerView.ViewHolder {

    TextView mTextView;
    public Exercise mExercise;
    LinearLayout mLinearLayout;

    public ExerciseViewHolder(final Context context, View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.textView);
        mLinearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);

        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, ShowExerciseActivity.class);
                i.putExtra("goal", mExercise.getGoal());
                i.putExtra("image_link", mExercise.getImage_file());
                i.putExtra("desc", mExercise.getDesc());

                context.startActivity(i);

            }
        });

    }

    public void bindData(Exercise exercise) {
        mExercise = exercise;
        mTextView.setText(mExercise.getGoal());

    }
}
