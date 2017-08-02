package com.example.elmira.sci.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.elmira.sci.ProfileCustomList;
import com.example.elmira.sci.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.example.elmira.sci.R.array.profileList;
import static com.example.elmira.sci.R.id.profile_list_view;

public class ProfileActivity extends AppCompatActivity {
    TextView cell_number;
    SharedPreferences pref;
    String[] profileStringArray;
    String[] pValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pref = getSharedPreferences("MyUser", Context.MODE_PRIVATE);

        cell_number = (TextView) findViewById(R.id.cellNumberTextView);
        cell_number.setText("+98" + pref.getString("phone_number", null));

        profileStringArray = getResources().getStringArray(profileList);
        String[] profileValues = {pref.getString("email", null), pref.getString("birthday", null),
                pref.getString("sci_type", null), pref.getString("experience_level", null),
                pref.getString("upper_strength", null), pref.getString("sex", null),
                pref.getString("h_real", null), pref.getString("w_real", null)};
        pValues = profileValues;
        ProfileCustomList pCustomList = new ProfileCustomList(this, profileStringArray, pValues);

        ListView profileList = (ListView) findViewById(profile_list_view);
        profileList.setAdapter(pCustomList);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_help) {
            Intent i = new Intent(ProfileActivity.this, HelpActivity.class);
            i.putExtra("caller", "profile_help");
            startActivity(i);
        }

        if (id == R.id.action_edit_profile) {
            Intent i;
            i = new Intent(ProfileActivity.this, EditProfileActivity.class);
            startActivity(i);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        String[] profileValues = {pref.getString("email", null), pref.getString("birthday", null),
                pref.getString("sci_type", null), pref.getString("experience_level", null),
                pref.getString("upper_strength", null), pref.getString("sex", null),
                pref.getString("h_real", null), pref.getString("w_real", null)};

        pValues = profileValues;
    }
}
