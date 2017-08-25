package ir.elmirayafteh.spinalcordinjury.sci.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ir.elmirayafteh.spinalcordinjury.sci.DataBaseCheck;
import ir.elmirayafteh.spinalcordinjury.sci.FitnessCalculator;
import ir.elmirayafteh.spinalcordinjury.sci.HeightConverter;
import ir.elmirayafteh.spinalcordinjury.sci.MyPreferences;
import ir.elmirayafteh.spinalcordinjury.sci.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static java.lang.Integer.parseInt;

public class EditProfileActivity extends AppCompatActivity {
    public final String[] monthList = {"فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد",
            "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"};
    final int[] leap_year = {1280, 1284, 1288, 1292, 1296, 1300, 1305, 1309, 1313, 1317, 1321, 1325,
            1329, 1333, 1337, 1342, 1346, 1350, 1354, 1358, 1362, 1366, 1370, 1375, 1379, 1383,
            1387, 1391, 1395};
    int h_real;
    int w_real;
    int mYear;
    int mMonth;
    int age;

    String[] test;

    Spinner gender_sp;
    Spinner sci_sp;
    Spinner xp_sp;
    Spinner up_sp;

    TextView emailTextView;
    TextView bDayTextView;
    TextView heightTextView;
    TextView weightTextView;
    TextView nv_email;
    TextView nv_bDay;
    TextView nv_height;
    TextView nv_weight;

    EditText heightEdit;
    EditText editEmail;
    EditText weightEdit;

    Button btn_email;
    Button btn_bDay;
    Button btn_weight;
    Button btn_height;
    Button btn_change_pass;

    LinearLayout linearLayout;

    NumberPicker dPicker;
    NumberPicker mPicker;
    NumberPicker yPicker;

    SharedPreferences pref;
    public SharedPreferences.OnSharedPreferenceChangeListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gender_sp = (Spinner) findViewById(R.id.genderSpinner);
        sci_sp = (Spinner) findViewById(R.id.sciTypeSpinner);
        xp_sp = (Spinner) findViewById(R.id.xpLevelSpinner);
        up_sp = (Spinner) findViewById(R.id.upStrSpinner);

        emailTextView = (TextView) findViewById(R.id.emailTextView);
        bDayTextView = (TextView) findViewById(R.id.dateOfBirthTextView);
        heightTextView = (TextView) findViewById(R.id.heightTextView);
        weightTextView = (TextView) findViewById(R.id.weightTextView);
        nv_email = (TextView) findViewById(R.id.not_valid_email);
        nv_bDay = (TextView) findViewById(R.id.not_valid_dob);
        nv_height = (TextView) findViewById(R.id.not_valid_height);
        nv_weight = (TextView) findViewById(R.id.not_valid_weight);

        heightEdit = (EditText) findViewById(R.id.heightEdit);
        editEmail = (EditText) findViewById(R.id.editEmail);
        weightEdit = (EditText) findViewById(R.id.weightEdit);

        btn_email = (Button) findViewById(R.id.btn_email);
        btn_bDay = (Button) findViewById(R.id.btn_dob);
        btn_weight = (Button) findViewById(R.id.btn_weight);
        btn_height = (Button) findViewById(R.id.btn_height);
        btn_change_pass = (Button) findViewById(R.id.change_pass_button);

        linearLayout = (LinearLayout) findViewById(R.id.editDateOfBirth);

        dPicker = (NumberPicker) findViewById(R.id.dayPicker);
        mPicker = (NumberPicker) findViewById(R.id.monthPicker);
        yPicker = (NumberPicker) findViewById(R.id.yearPicker);

        pref = getSharedPreferences("MyUser", Context.MODE_PRIVATE);

        gender_sp.setSelection(getIndex(gender_sp, pref.getString("gender", null)));
        sci_sp.setSelection(getIndex(sci_sp, pref.getString("sci_type", null)));
        xp_sp.setSelection(getIndex(xp_sp, pref.getString("experience_level", null)));
        up_sp.setSelection(getIndex(up_sp, pref.getString("upper_strength", null)));

        btn_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                i = new Intent(EditProfileActivity.this, ChangePasswordActivity.class);
                i.putExtra("caller", "edit_profile");
                i.putExtra("phone_number", pref.getString("phone_number", null));
                startActivity(i);
            }
        });


        emailTextView.setText(pref.getString("email", null));
        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (btn_email.getText().toString().equals("ویرایش")) {
                    emailTextView.setVisibility(View.GONE);
                    editEmail.setVisibility(View.VISIBLE);
                    btn_email.setText(R.string.editing);
                    btn_email.setTextColor(Color.WHITE);
                    btn_email.setBackgroundResource(R.drawable.red_button_shape);

                } else {
                    emailTextView.setVisibility(View.VISIBLE);
                    editEmail.setVisibility(View.GONE);
                    editEmail.setText("");
                    btn_email.setText(R.string.edit);
                    btn_email.setTextColor(Color.BLACK);
                    btn_email.setBackgroundResource(R.drawable.blue_button_shape);
                }
            }
        });


        bDayTextView.setText(pref.getString("birthday", null));
        String b_day = pref.getString("birthday", null);
        test = b_day.split("-");
        btn_bDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (btn_bDay.getText().toString().equals("ویرایش")) {
                    bDayTextView.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                    btn_bDay.setText(R.string.editing);
                    btn_bDay.setTextColor(Color.WHITE);
                    btn_bDay.setBackgroundResource(R.drawable.red_button_shape);

                    dPicker.setMaxValue(31);
                    dPicker.setMinValue(1);
                    dPicker.setValue(parseInt(test[2]));
                    dPicker.setWrapSelectorWheel(false);

                    mPicker.setMinValue(0);
                    mPicker.setMaxValue(11);
                    mPicker.setValue(parseInt(test[1]) - 1);
                    mPicker.setDisplayedValues(monthList);
                    mPicker.setWrapSelectorWheel(false);

                    yPicker.setMaxValue(1396);
                    yPicker.setMinValue(1280);
                    yPicker.setValue(parseInt(test[0]));
                    yPicker.setWrapSelectorWheel(false);

                } else {
                    bDayTextView.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                    btn_bDay.setText(R.string.edit);
                    btn_bDay.setTextColor(Color.BLACK);
                    btn_bDay.setBackgroundResource(R.drawable.blue_button_shape);
                }
            }
        });


        heightTextView.setText(pref.getString("h_real", null));
        btn_height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (btn_height.getText().toString().equals("ویرایش")) {
                    heightTextView.setVisibility(View.GONE);
                    heightEdit.setVisibility(View.VISIBLE);
                    btn_height.setText(R.string.editing);
                    btn_height.setTextColor(Color.WHITE);
                    heightEdit.setHint(pref.getString("h_real", null));
                    btn_height.setBackgroundResource(R.drawable.red_button_shape);

                } else {
                    heightTextView.setVisibility(View.VISIBLE);
                    heightEdit.setVisibility(View.GONE);
                    heightEdit.setText("");
                    btn_height.setText(R.string.edit);
                    btn_height.setTextColor(Color.BLACK);
                    btn_height.setBackgroundResource(R.drawable.blue_button_shape);

                }
            }
        });

        weightTextView.setText(pref.getString("w_real", null));
        btn_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (btn_weight.getText().toString().equals("ویرایش")) {
                    weightTextView.setVisibility(View.GONE);
                    weightEdit.setVisibility(View.VISIBLE);
                    btn_weight.setText(R.string.editing);
                    btn_weight.setTextColor(Color.WHITE);
                    btn_weight.setBackgroundResource(R.drawable.red_button_shape);
                    weightEdit.setHint(pref.getString("w_real", null));

                } else {
                    weightTextView.setVisibility(View.VISIBLE);
                    weightEdit.setVisibility(View.GONE);
                    weightEdit.setText("");
                    btn_weight.setText(R.string.edit);
                    btn_weight.setTextColor(Color.BLACK);
                    btn_weight.setBackgroundResource(R.drawable.blue_button_shape);
                }

            }
        });


        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean b_email = true;
                Boolean b_height = true;
                Boolean b_weight = true;
                Boolean b_dob = true;

                nv_height.setVisibility(View.GONE);
                nv_weight.setVisibility(View.GONE);
                nv_email.setVisibility(View.GONE);
                nv_bDay.setVisibility(View.GONE);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                SharedPreferences.Editor editor = pref.edit();
                editor.putString("gender", gender_sp.getSelectedItem().toString());
                editor.putString("sci_type", sci_sp.getSelectedItem().toString());
                editor.putString("experience_level", xp_sp.getSelectedItem().toString());
                editor.putString("upper_strength", up_sp.getSelectedItem().toString());


                if (!editEmail.getText().toString().equals("")) {
                    if (isEmailValid(editEmail.getText().toString())) {
                        editor.putString("email", editEmail.getText().toString());

                    } else {
                        nv_email.setVisibility(View.VISIBLE);
                        b_email = false;
                    }
                }

                if (yPicker.getValue() != 0 && btn_bDay.getText().toString().equals("انصراف")) {
                    if (isBirthdayValid(yPicker.getValue(), mPicker.getValue() + 1, dPicker.getValue())) {
                        String birthday = String.valueOf(yPicker.getValue()).concat("-".concat(String.valueOf(mPicker.getValue() + 1).concat("-").concat(String.valueOf(dPicker.getValue()))));
                        editor.putString("birthday", birthday);
                        mYear = yPicker.getValue();
                        mMonth = mPicker.getValue() + 1;
                    } else {
                        nv_bDay.setVisibility(View.VISIBLE);
                        b_dob = false;
                    }
                } else {
                    editor.putString("birthday", bDayTextView.getText().toString());
                    String[] temp = bDayTextView.getText().toString().split("-");
                    mYear = parseInt(temp[0]);
                    mMonth = parseInt(temp[1]) + 1;
                }

                if (!heightEdit.getText().toString().equals("")) {
                    try {
                        h_real = parseInt(heightEdit.getText().toString());
                        if (h_real < 99 || h_real > 250) {
                            nv_height.setVisibility(View.VISIBLE);
                            b_height = false;
                        } else {
                            editor.putString("h_real", heightEdit.getText().toString());
                        }
                    } catch (NumberFormatException e) {
                        nv_height.setVisibility(View.VISIBLE);
                        b_height = false;
                    }
                } else {
                    h_real = parseInt(heightTextView.getText().toString());
                }

                if (!weightEdit.getText().toString().equals("")) {
                    try {
                        w_real = parseInt(weightEdit.getText().toString());
                        if (w_real < 34 || w_real > 200) {
                            nv_weight.setVisibility(View.VISIBLE);
                            b_weight = false;
                        } else {
                            editor.putString("w_real", weightEdit.getText().toString());
                        }
                    } catch (NumberFormatException e) {
                        nv_weight.setVisibility(View.VISIBLE);
                        b_weight = false;
                    }
                } else {
                    w_real = parseInt(weightTextView.getText().toString());
                }

                HeightConverter h_convert = new HeightConverter();
                FitnessCalculator f_calc = new FitnessCalculator();
                age = h_convert.ConvertAge(mYear, mMonth);

                editor.putString("height", h_convert.ConvertHeight(h_real, gender_sp.getSelectedItem().toString()));
                editor.putString("fitness", f_calc.FitnessCalc(h_real, w_real, age, gender_sp.getSelectedItem().toString()));

                if (b_email && b_dob && b_height && b_weight) {
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorDarkGreen)));
                    Snackbar.make(view, R.string.approved_message, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    editor.apply();

                    MyPreferences myPreferences = new MyPreferences(pref);
                    if (myPreferences.updateOnServer()) {
                        Snackbar.make(view, "اطلاعات با موفقیت بر روی سرور ذخیره شد.", Snackbar.LENGTH_LONG).show();
                    }
                    DataBaseCheck dataBaseCheck = new DataBaseCheck();
                    dataBaseCheck.dataBaseChecker(pref, EditProfileActivity.this);

                    btn_bDay.setText(R.string.edit);
                    btn_email.setText(R.string.edit);
                    btn_weight.setText(R.string.edit);
                    btn_height.setText(R.string.edit);

                    btn_bDay.setTextColor(Color.BLACK);
                    btn_email.setTextColor(Color.BLACK);
                    btn_weight.setTextColor(Color.BLACK);
                    btn_height.setTextColor(Color.BLACK);

                    btn_bDay.setBackgroundResource(R.drawable.blue_button_shape);
                    btn_email.setBackgroundResource(R.drawable.blue_button_shape);
                    btn_weight.setBackgroundResource(R.drawable.blue_button_shape);
                    btn_height.setBackgroundResource(R.drawable.blue_button_shape);

                    editEmail.setVisibility(View.GONE);
                    heightEdit.setVisibility(View.GONE);
                    weightEdit.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.GONE);

                    weightTextView.setVisibility(View.VISIBLE);
                    heightTextView.setVisibility(View.VISIBLE);
                    emailTextView.setVisibility(View.VISIBLE);
                    bDayTextView.setVisibility(View.VISIBLE);

                    weightTextView.setText(pref.getString("w_real", null));
                    heightTextView.setText(pref.getString("h_real", null));
                    emailTextView.setText(pref.getString("email", null));
                    bDayTextView.setText(pref.getString("birthday", null));

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                        }
                    }, 5000);


                }

            }
        });


    }

    private int getIndex(Spinner spinner, String myString) {

        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(myString)) {
                index = i;
            }
        }
        return index;
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

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_help) {
            Intent i = new Intent(EditProfileActivity.this, HelpActivity.class);
            i.putExtra("caller", "edit_profile_help");
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

}
