package ir.elmirayafteh.spinalcordinjury.sci.activities;


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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ir.elmirayafteh.spinalcordinjury.sci.ChangePassword;
import ir.elmirayafteh.spinalcordinjury.sci.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChangePasswordActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    EditText old_pass;
    EditText new_pass;
    EditText conf_pass;
    TextView mHelp;
    String caller;
    String new_one;
    String old_one;
    String phone_number;
    Button ok_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        old_pass = (EditText) findViewById(R.id.oldPassword);
        new_pass = (EditText) findViewById(R.id.newPassword);
        conf_pass = (EditText) findViewById(R.id.confirmPassword);
        ok_button = (Button) findViewById(R.id.ok_button);
        mHelp = (TextView) findViewById(R.id.password_help);

        Intent iCaller = getIntent();
        caller = iCaller.getStringExtra("caller");
        phone_number = iCaller.getStringExtra("phone_number");

        if (caller.equals("edit_profile")) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            ok_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    old_one = old_pass.getText().toString();
                    new_one = new_pass.getText().toString();

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    if (checkLength(new_one)) {

                        if (isValidPassword(new_one)) {

                            if (new_one.equals(conf_pass.getText().toString())) {

                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl("http://elmirayafteh.ir/sciwebservice/").addConverterFactory(GsonConverterFactory.create())
                                        .build();

                                ChangePassword uService = retrofit.create(ChangePassword.class);

                                uService.changePassword(phone_number, old_one, new_one).enqueue(new Callback<String>() {


                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        if (response.isSuccessful()) {
                                            SharedPreferences sh_pref = getSharedPreferences("MyUser", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sh_pref.edit();
                                            editor.putString("password", response.body());
                                            editor.apply();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                    }
//                                    Toast.makeText(ChangePasswordActivity.this, R.string.wrong_password, Toast.LENGTH_LONG).show();

                                });

                                Snackbar.make(view, ir.elmirayafteh.spinalcordinjury.sci.R.string.approved_password, Snackbar.LENGTH_LONG).show();
                                mHelp.setVisibility(View.GONE);
                                old_pass.setText("");
                                new_pass.setText("");
                                conf_pass.setText("");

                            } else {
                                Toast.makeText(ChangePasswordActivity.this, ir.elmirayafteh.spinalcordinjury.sci.R.string.passwords_dont_match, Toast.LENGTH_LONG).show();
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

                }
            });


        } else {
            old_pass.setVisibility(View.GONE);
            TextView old_text = (TextView) findViewById(R.id.oldPasswordTextView);
            old_text.setVisibility(View.GONE);
            old_one = "FORGOT";

            ok_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new_one = new_pass.getText().toString();

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


                    if (checkLength(new_one)) {

                        if (isValidPassword(new_one)) {

                            if (new_one.equals(conf_pass.getText().toString())) {

                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl("http://elmirayafteh.ir/sciwebservice/").addConverterFactory(GsonConverterFactory.create())
                                        .build();

                                ChangePassword uService = retrofit.create(ChangePassword.class);

                                uService.changePassword(phone_number, old_one, new_one).enqueue(new Callback<String>() {


                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        if (response.isSuccessful()) {

                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("phone_number", phone_number);
                                            editor.apply();

                                            Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                    }

                                });


                            } else {
                                Toast.makeText(ChangePasswordActivity.this, ir.elmirayafteh.spinalcordinjury.sci.R.string.passwords_dont_match, Toast.LENGTH_LONG).show();
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
                }
            });

        }

        Button cancel_button = (Button) findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    public static boolean checkLength(String password) {
        if (password.length() < 6) {
            return false;
        } else {
            return true;
        }

    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z]).{4,20}";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        if (caller.equals("edit_profile")){
            getMenuInflater().inflate(R.menu.menu_default, menu);
        }

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
