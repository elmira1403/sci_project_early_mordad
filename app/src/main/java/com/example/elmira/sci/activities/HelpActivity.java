package com.example.elmira.sci.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.elmira.sci.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HelpActivity extends AppCompatActivity {

    String mCaller;
    String[] help_string_array;
    String[] help_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textView = (TextView) findViewById(R.id.help_text);

        Intent intent = getIntent();
        mCaller = intent.getStringExtra("caller");

        help_array = getResources().getStringArray(R.array.helpArray);
        help_string_array = getResources().getStringArray(R.array.helpStringArray);
        for (String s : help_array) {
            int i = s.indexOf(mCaller);
            if (i >= 0) {
                textView.setText(help_string_array[i]);
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
