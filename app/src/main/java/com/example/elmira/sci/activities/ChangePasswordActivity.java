package com.example.elmira.sci.activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elmira.sci.MyPreferences;
import com.example.elmira.sci.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText old_pass;
    EditText new_pass;
    EditText conf_pass;
    TextView mHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button ok_button = (Button) findViewById(R.id.ok_button);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                old_pass = (EditText) findViewById(R.id.oldPassword);
                new_pass = (EditText) findViewById(R.id.newPassword);
                conf_pass = (EditText) findViewById(R.id.confirmPassword);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                SharedPreferences sh_pref = getSharedPreferences("MyUser", MODE_PRIVATE);

                mHelp= (TextView) findViewById(R.id.password_help);

                if (old_pass.getText().toString().equals(sh_pref.getString("password", null))) {

                    if (checkLength(new_pass.getText().toString())) {

                        if (isValidPassword(new_pass.getText().toString())){

                            if (new_pass.getText().toString().equals(conf_pass.getText().toString())) {

                                SharedPreferences.Editor editor = sh_pref.edit();
                                editor.putString("password", new_pass.getText().toString());
                                editor.apply();

                                MyPreferences myPreferences = new MyPreferences(sh_pref);
                                Snackbar.make(view, R.string.approved_password, Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                mHelp.setVisibility(View.GONE);
                                old_pass.setText("");
                                new_pass.setText("");
                                conf_pass.setText("");
                                myPreferences.updateOnServer();

                            } else {
                                Toast.makeText(ChangePasswordActivity.this, R.string.passwords_dont_match, Toast.LENGTH_LONG).show();
                                mHelp.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(ChangePasswordActivity.this, "گذرواژه جدید معتبر نیست", Toast.LENGTH_LONG).show();
                            mHelp.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "گذرواژه می بایست حداقل 6 کاراکتر باشد", Toast.LENGTH_LONG).show();
                        mHelp.setVisibility(View.VISIBLE);
                    }


                } else {
                    Toast.makeText(ChangePasswordActivity.this, R.string.wrong_password, Toast.LENGTH_LONG).show();

                }
            }
        });

        Button cancel_button = (Button) findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public static boolean checkLength(String password) {
        if (password.length()<6){
            return false;
        } else {
            return true;
        }

    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z]).{4,20}";
//        final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$).{4,}";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_help) {
            Intent i = new Intent(ChangePasswordActivity.this, HelpActivity.class);
            i.putExtra("caller", "change_password_help");
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
