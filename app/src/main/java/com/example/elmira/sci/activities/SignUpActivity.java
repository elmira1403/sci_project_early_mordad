package com.example.elmira.sci.activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elmira.sci.FitnessCalculator;
import com.example.elmira.sci.HeightConverter;
import com.example.elmira.sci.MyPreferences;
import com.example.elmira.sci.R;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.example.elmira.sci.R.id.editEmail;
import static java.lang.Integer.parseInt;

public class SignUpActivity extends AppCompatActivity {

    public final String[] monthList = {"فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد",
            "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"};
    final int[] leap_year = {1280, 1284, 1288, 1292, 1296, 1300, 1305, 1309, 1313, 1317, 1321, 1325,
            1329, 1333, 1337, 1342, 1346, 1350, 1354, 1358, 1362, 1366, 1370, 1375, 1379, 1383,
            1387, 1391, 1395};
    public final String USER = "MyUser";


    SharedPreferences sharedPreferences;
    String phone_number;
    String password;

    LinearLayout bDayLayout;
    LinearLayout set_password;
    ScrollView complete_form;

    Button pass_ok;
    Button form_ok;

    TextView password_help;
    TextView email_error;
    TextView height_error;
    TextView weight_error;
    TextView gender_error;
    TextView xp_error;
    TextView str_error;
    TextView dob_error;

    EditText password_edit;
    EditText confirm_password_edit;
    EditText email_edit;
    EditText height_edit;
    EditText weight_edit;

    NumberPicker dPicker;
    NumberPicker mPicker;
    NumberPicker yPicker;

    RadioGroup gender;
    RadioGroup xp_level;
    RadioGroup up_str;

    RadioButton gender_btn;
    RadioButton xp_level_btn;
    RadioButton up_str_btn;

    int selectedGender;
    int selectedXp;
    int selectedStr;
    int h_real;
    int w_real;

    Spinner sci_sp;
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent p_intent = getIntent();
        phone_number = p_intent.getStringExtra("phone_number");

        set_password = (LinearLayout) findViewById(R.id.setting_password);
        complete_form = (ScrollView) findViewById(R.id.completing_form);

        password_help = (TextView) findViewById(R.id.password_help);

        password_edit = (EditText) findViewById(R.id.passwordEdit);
        confirm_password_edit = (EditText) findViewById(R.id.confirmPasswordEdit);

        pass_ok = (Button) findViewById(R.id.button_pass_ok);
        pass_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                password = password_edit.getText().toString();

                if (isValidPassword(password)) {

                    if (password.equals(confirm_password_edit.getText().toString())) {

                        set_password.setVisibility(View.GONE);
                        complete_form.setVisibility(View.VISIBLE);

                    } else {
                        Toast.makeText(SignUpActivity.this, R.string.passwords_dont_match, Toast.LENGTH_LONG).show();
                        password_edit.setText("");
                        confirm_password_edit.setText("");
                    }

                } else {
                    Toast.makeText(SignUpActivity.this, "گذروازه انتخابی معتبر نیست", Toast.LENGTH_LONG).show();
                    password_edit.setText("");
                    confirm_password_edit.setText("");
                    password_help.setVisibility(View.VISIBLE);
                }
            }
        });

        bDayLayout = (LinearLayout) findViewById(R.id.editDateOfBirth);
        bDayLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        dPicker = (NumberPicker) findViewById(R.id.day_picker);
        mPicker = (NumberPicker) findViewById(R.id.month_picker);
        yPicker = (NumberPicker) findViewById(R.id.year_picker);

        dPicker.setMaxValue(31);
        dPicker.setMinValue(1);
        dPicker.setValue(15);
        dPicker.setWrapSelectorWheel(false);

        mPicker.setMinValue(0);
        mPicker.setMaxValue(11);
        mPicker.setValue(6);
        mPicker.setDisplayedValues(monthList);
        mPicker.setWrapSelectorWheel(false);

        yPicker.setMaxValue(1396);
        yPicker.setMinValue(1280);
        yPicker.setValue(1340);
        yPicker.setWrapSelectorWheel(false);

        gender = (RadioGroup) findViewById(R.id.gender_radio);
        xp_level = (RadioGroup) findViewById(R.id.xp_level_radio);
        up_str = (RadioGroup) findViewById(R.id.up_str_radio);

        sci_sp = (Spinner) findViewById(R.id.sciTypeSpinner);
        email_edit = (EditText) findViewById(editEmail);
        email_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
        height_edit = (EditText) findViewById(R.id.editHeight);
        height_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
        weight_edit = (EditText) findViewById(R.id.editWeight);
        weight_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        form_ok = (Button) findViewById(R.id.button_form_ok);
        form_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                if (isFormAccepted()) {
                    savePreferences();
                    MyPreferences mResult = new MyPreferences(sharedPreferences);
                    mResult.saveOnServer();
                    mResult.setListener(new MyPreferences.RetroResultListener() {
                        @Override
                        public void onDataLoaded(String result) {
                            if (Boolean.parseBoolean(result)) {
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Snackbar.make(getCurrentFocus(), "لطفا مجددا فرم را ارسال کنید", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });

                } else {
                    Snackbar.make(view, "لطفا فرم را اصلاح کنید", Snackbar.LENGTH_LONG).show();
                }

            }
        });


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public static boolean isValidPassword(final String password) {

        final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z]).{4,20}";
        Pattern pattern;
        Matcher matcher;
        Boolean result;

        if (password.length() < 6) {
            result = false;
        } else {
            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(password);
            result = matcher.matches();
        }

        return result;
    }

    public boolean isBirthdayValid(int year, int month, int day) {
        Boolean result = true;

        if (month > 6 && day == 31) {
            result = false;
        } else if (month == 12 && day == 30 && !Arrays.asList(leap_year).contains(year)) {
            result = false;
        }

        return result;
    }

    public boolean isFormAccepted() {
        Boolean result = true;

        email_error = (TextView) findViewById(R.id.email_not_valid);
        height_error = (TextView) findViewById(R.id.height_not_valid);
        weight_error = (TextView) findViewById(R.id.weight_not_valid);
        gender_error = (TextView) findViewById(R.id.gender_not_valid);
        xp_error = (TextView) findViewById(R.id.xp_level_not_valid);
        str_error = (TextView) findViewById(R.id.str_not_valid);
        dob_error = (TextView) findViewById(R.id.dob_not_valid);

        email_error.setVisibility(View.GONE);
        height_error.setVisibility(View.GONE);
        weight_error.setVisibility(View.GONE);
        gender_error.setVisibility(View.GONE);
        xp_error.setVisibility(View.GONE);
        str_error.setVisibility(View.GONE);
        dob_error.setVisibility(View.GONE);

        if (!email_edit.getText().toString().equals("")) {
            if (!EditProfileActivity.isEmailValid(email_edit.getText().toString())) {
                email_error.setVisibility(View.VISIBLE);
                result = false;
            }
        }

        if (!height_edit.getText().toString().equals("")) {
            try {
                h_real = parseInt(height_edit.getText().toString());
                if (h_real < 99 || h_real > 250) {
                    height_error.setVisibility(View.VISIBLE);
                    result = false;
                }
            } catch (NumberFormatException e) {
                height_error.setVisibility(View.VISIBLE);
                result = false;
            }
        } else {
            height_error.setVisibility(View.VISIBLE);
            result = false;
        }


        if (!weight_edit.getText().toString().equals("")) {
            try {
                w_real = parseInt(weight_edit.getText().toString());
                if (w_real < 34 || w_real > 200) {
                    weight_error.setVisibility(View.VISIBLE);
                    result = false;
                }
            } catch (NumberFormatException e) {
                weight_error.setVisibility(View.VISIBLE);
                result = false;
            }
        } else {
            weight_error.setVisibility(View.VISIBLE);
            result = false;
        }

        selectedGender = gender.getCheckedRadioButtonId();
        selectedXp = xp_level.getCheckedRadioButtonId();
        selectedStr = up_str.getCheckedRadioButtonId();

        if (selectedGender == -1) {
            gender_error.setVisibility(View.VISIBLE);
            result = false;
        }

        if (selectedXp == -1) {
            xp_error.setVisibility(View.VISIBLE);
            result = false;
        }

        if (selectedStr == -1) {
            str_error.setVisibility(View.VISIBLE);
            result = false;
        }

        if (!isBirthdayValid(yPicker.getValue(), mPicker.getValue() + 1, dPicker.getValue())) {
            dob_error.setVisibility(View.VISIBLE);
            result = false;
        }

        return result;
    }

    public void savePreferences() {

        sharedPreferences = getSharedPreferences(USER, MODE_PRIVATE);
        gender_btn = (RadioButton) findViewById(selectedGender);
        xp_level_btn = (RadioButton) findViewById(selectedXp);
        up_str_btn = (RadioButton) findViewById(selectedStr);
        String birthday = String.valueOf(yPicker.getValue()).concat("-".concat(String.valueOf(mPicker.getValue() + 1).concat("-").concat(String.valueOf(dPicker.getValue()))));

        HeightConverter h_convert = new HeightConverter();
        FitnessCalculator f_calc = new FitnessCalculator();
        int age = h_convert.ConvertAge(yPicker.getValue(), mPicker.getValue() + 1);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("phone_number", phone_number);
        editor.putString("password", password);
        editor.putString("email", email_edit.getText().toString());
        editor.putString("birthday", birthday);
        editor.putString("sex", gender_btn.getText().toString());
        editor.putString("sci_type", sci_sp.getSelectedItem().toString());
        editor.putString("experience_level", xp_level_btn.getText().toString().split(" \\(")[0]);
        editor.putString("upper_strength", up_str_btn.getText().toString().split(" \\(")[0]);
        editor.putString("h_real", String.valueOf(h_real));
        editor.putString("w_real", String.valueOf(w_real));
        editor.putString("height", h_convert.ConvertHeight(h_real, gender_btn.getText().toString()));
        editor.putString("fitness", f_calc.FitnessCalc(h_real, w_real, age, gender_btn.getText().toString()));

        editor.apply();
    }


    @Override
    public void onBackPressed() {

        if (set_password.getVisibility() == View.VISIBLE) {
            super.onBackPressed();
            Intent intent = new Intent(SignUpActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish();

        } else if (complete_form.getVisibility() == View.VISIBLE) {
            complete_form.setVisibility(View.GONE);
            set_password.setVisibility(View.VISIBLE);

        }
    }

}
