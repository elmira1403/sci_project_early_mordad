package ir.elmirayafteh.spinalcordinjury.sci.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import ir.elmirayafteh.spinalcordinjury.sci.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HelpActivity extends AppCompatActivity {

    String mCaller;
    String[] help_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView imageView = (ImageView) findViewById(R.id.help_image);

        Intent intent = getIntent();
        mCaller = intent.getStringExtra("caller");

        help_array = getResources().getStringArray(ir.elmirayafteh.spinalcordinjury.sci.R.array.helpArray);
        for (int i = 0; i < help_array.length; i++) {
            if (help_array[i].contains(mCaller)) {

                switch (help_array[i]) {
                    case "main_help":
                        imageView.setImageResource(R.drawable.help_main);
                        break;

                    case "recommend_help":
                        imageView.setImageResource(R.drawable.help_rec);
                        break;

                    case "show_case_help":
                        imageView.setImageResource(R.drawable.help_show_case);
                        break;

                    case "exercise_help":
                        imageView.setImageResource(R.drawable.help_exercise);
                        break;

                    case "show_exercise_help":
                        imageView.setImageResource(R.drawable.help_show_exercise);
                        break;

                    case "profile_help":
                        imageView.setImageResource(R.drawable.help_profile);
                        break;

                    case "edit_profile_help":
                        imageView.setImageResource(R.drawable.help_edit_profile);
                        break;

                    case "change_password_help":
                        imageView.setImageResource(R.drawable.help_change_pass);
                        break;

                    case "fav_help":
                        imageView.setImageResource(R.drawable.help_fav);
                        break;
                }
                break;
            }

        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
