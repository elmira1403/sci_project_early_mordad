package ir.elmirayafteh.spinalcordinjury.sci;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.elmirayafteh.spinalcordinjury.sci.activities.ShowCaseActivity;

public class CaseViewHolder extends RecyclerView.ViewHolder {

    TextView mTextView;
    public Case mCase;
    LinearLayout mLinearLayout;

    public CaseViewHolder(final Context context, View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.textView);
        mLinearLayout = (LinearLayout)  itemView.findViewById(R.id.linearLayout);

        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, ShowCaseActivity.class);
                i.putExtra("case_subject", mCase.getSubject());
                i.putExtra("text", mCase.getText_file());
                i.putExtra("voice_link", mCase.getVoice_file());
                i.putExtra("desc", mCase.getDesc());
                i.putExtra("video_link", mCase.getVideo_file());

                context.startActivity(i);

            }
        });

    }

    public void bindData(Case case1) {
        mCase = case1;
        mTextView.setText(mCase.getSubject());

    }
}
