package com.example.elmira.sci;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.elmira.sci.activities.ShowCaseActivity;

public class CaseViewHolder extends RecyclerView.ViewHolder {

    TextView mTextView;
    ImageView casePic;
    public Case mCase;
    LinearLayout mLinearLayout;

    public CaseViewHolder(final Context context, View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.textView);
        mLinearLayout = (LinearLayout)  itemView.findViewById(R.id.linearLayout);
//        casePic = (ImageView) itemView.findViewById(R.id.imageView);

        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, ShowCaseActivity.class);
                i.putExtra("case_subject", mCase.getSubject());
                i.putExtra("text", mCase.getText_file());
//                i.putExtra("poster", mCase.getImage());
                i.putExtra("voice_link", mCase.getVoice_file());
                i.putExtra("desc", mCase.getDesc());
                i.putExtra("video_link", mCase.getVideo_file());

                context.startActivity(i);

            }
        });

//        casePic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(context, ShowCaseActivity.class);
//                i.putExtra("case_subject", mCase.getTitle());
//                i.putExtra("poster", mCase.getImage());
//                i.putExtra("voice_link", mCase.getFile());
//                i.putExtra("video_link", "http://s1.srv2.mihandl.in/FullVideoKhareji/Britney%20Spears/Britney%20Spears%20-%20Till%20The%20World%20Ends%20%28%20MihanMusic%20%29.mp4");
//                context.startActivity(i);
//            }
//        });
    }

    public void bindData(final Context context, Case case1) {
        mCase = case1;
        mTextView.setText(mCase.getSubject());
//        Picasso.with(context).load(mCase.getImage()).into(casePic);

    }
}
