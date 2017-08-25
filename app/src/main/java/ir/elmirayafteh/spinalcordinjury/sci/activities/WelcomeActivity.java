package ir.elmirayafteh.spinalcordinjury.sci.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ir.elmirayafteh.spinalcordinjury.sci.R;
import ir.elmirayafteh.spinalcordinjury.sci.SMS;
import ir.elmirayafteh.spinalcordinjury.sci.SMSPanel;
import ir.elmirayafteh.spinalcordinjury.sci.User;
import ir.elmirayafteh.spinalcordinjury.sci.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class WelcomeActivity extends AppCompatActivity {
    public final String USER = "MyUser";

    Button btnSignIn;
    Button btnSignIn2;
    Button btnSignUp;
    Button btnSignUp2;
    Button verify;

    EditText pass;
    EditText ph_number_sign_in;
    EditText ph_number_sign_up;
    EditText verification_edit;

    TextView forgot_password;
    TextView resend_code;

    LinearLayout welcome_layout;
    LinearLayout sign_in_layout;
    LinearLayout sign_up_layout;
    LinearLayout verification_layout;

    String original_code;
    String phoneNumber;
    String password;
    String mUsername;
    String mPassword;
    Retrofit retrofit;
    UserInfo mUserInfo;
    SharedPreferences sharedpreferences;
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcome);


        welcome_layout = (LinearLayout) findViewById(R.id.welcomeLayout);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                welcome_layout.setVisibility(View.VISIBLE);
            }
        }, 3000);


        sharedpreferences = this.getSharedPreferences(USER, MODE_PRIVATE);
        mUsername = sharedpreferences.getString("phone_number", null);
        mPassword = sharedpreferences.getString("password", null);

        if (mUsername != null && mPassword != null && !mUsername.isEmpty() && !mPassword.isEmpty()) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://elmirayafteh.ir/sciwebservice/").addConverterFactory(GsonConverterFactory.create())
                    .build();

            User uService = retrofit.create(User.class);

            uService.getUserInfo(mUsername, mPassword).enqueue(new Callback<UserInfo>() {
                @Override
                public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                    if (response.isSuccessful()) {
                        mUserInfo = response.body();

                        if (!mUserInfo.user_exist.equals("not_exist")) {
                            sharedpreferences = getSharedPreferences(USER, MODE_PRIVATE);

                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("phone_number", mUsername);
                            editor.putString("password", mUserInfo.password);
                            editor.putString("email", mUserInfo.email);
                            editor.putString("birthday", mUserInfo.birthday);
                            editor.putString("gender", mUserInfo.gender);
                            editor.putString("sci_type", mUserInfo.sci_type);
                            editor.putString("experience_level", mUserInfo.experience_level);
                            editor.putString("upper_strength", mUserInfo.upper_strength);
                            editor.putString("height", mUserInfo.height);
                            editor.putString("fitness", mUserInfo.fitness);
                            editor.putString("h_real", mUserInfo.h_real);
                            editor.putString("w_real", mUserInfo.w_real);
                            editor.apply();

                            Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            welcome_layout.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserInfo> call, Throwable t) {
                    welcome_layout.setVisibility(View.VISIBLE);
                }
            });
        } else {
            welcome_layout.setVisibility(View.VISIBLE);

        }

        ph_number_sign_in = (EditText) findViewById(R.id.phoneNumberEditSignIn);
        pass = (EditText) findViewById(R.id.passwordEditSignIn);
        ph_number_sign_up = (EditText) findViewById(R.id.phoneNumberEditSignUp);

        sign_in_layout = (LinearLayout) findViewById(R.id.signInLayout);
        sign_up_layout = (LinearLayout) findViewById(R.id.signUpLayout);
        verification_layout = (LinearLayout) findViewById(R.id.verification_code);

        btnSignIn = (Button) findViewById(R.id.button_sign_in);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                welcome_layout.setVisibility(View.GONE);
                sign_in_layout.setVisibility(View.VISIBLE);

                pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {

                        if (!hasFocus) {
                            imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }

                    }
                });

                btnSignIn2 = (Button) findViewById(R.id.button_sign_in2);
                btnSignIn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                        phoneNumber = ph_number_sign_in.getText().toString();
                        password = pass.getText().toString();

                        retrofit = new Retrofit.Builder()
                                .baseUrl("http://elmirayafteh.ir/sciwebservice/").addConverterFactory(GsonConverterFactory.create())
                                .build();

                        User uService = retrofit.create(User.class);

                        uService.getUserInfo(phoneNumber, password).enqueue(new Callback<UserInfo>() {
                            @Override
                            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                                if (response.isSuccessful()) {
                                    mUserInfo = response.body();

                                    if (!mUserInfo.user_exist.equals("not_exist")) {

                                        sharedpreferences = getSharedPreferences(USER, MODE_PRIVATE);

                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString("phone_number", phoneNumber);
                                        editor.putString("password", mUserInfo.password);
                                        editor.putString("email", mUserInfo.email);
                                        editor.putString("birthday", mUserInfo.birthday);
                                        editor.putString("gender", mUserInfo.gender);
                                        editor.putString("sci_type", mUserInfo.sci_type);
                                        editor.putString("experience_level", mUserInfo.experience_level);
                                        editor.putString("upper_strength", mUserInfo.upper_strength);
                                        editor.putString("height", mUserInfo.height);
                                        editor.putString("fitness", mUserInfo.fitness);
                                        editor.putString("h_real", mUserInfo.h_real);
                                        editor.putString("w_real", mUserInfo.w_real);
                                        editor.apply();


                                        Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        Toast.makeText(WelcomeActivity.this, ir.elmirayafteh.spinalcordinjury.sci.R.string.account_error, Toast.LENGTH_LONG).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<UserInfo> call, Throwable t) {
                                Toast.makeText(WelcomeActivity.this, "Error in Getting User Info", Toast.LENGTH_LONG).show();
                            }
                        });

                        pass.setText("");

                    }
                });

                forgot_password = (TextView) findViewById(R.id.forgetPassword);
                forgot_password.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        sign_in_layout.setVisibility(View.GONE);
                        sign_up_layout.setVisibility(View.VISIBLE);

                        ph_number_sign_up.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View view, boolean hasFocus) {
                                if (!hasFocus) {
                                    imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                }
                            }
                        });

                        btnSignUp2 = (Button) findViewById(R.id.button_sign_up2);
                        btnSignUp2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                                phoneNumber = ph_number_sign_up.getText().toString();

                                if (isNumberValid(phoneNumber)) {
                                    sign_up_layout.setVisibility(View.GONE);
                                    verification_layout.setVisibility(View.VISIBLE);
                                    verify = (Button) findViewById(R.id.button_verify);
                                    verification_edit = (EditText) findViewById(R.id.edit_code_verify);

                                    Retrofit sms_retrofit = new Retrofit.Builder().baseUrl("http://elmirayafteh.ir/sciwebservice/")
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();

                                    SMSPanel smsService = sms_retrofit.create(SMSPanel.class);

                                    original_code = UUID.randomUUID().toString().substring(0, 5);

                                    smsService.SendSms(phoneNumber, original_code).enqueue(new Callback<SMS>() {
                                        @Override
                                        public void onResponse(Call<SMS> call, Response<SMS> response) {

                                        }

                                        @Override
                                        public void onFailure(Call<SMS> call, Throwable t) {

                                        }
                                    });


                                    verify.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            Intent intent = new Intent(WelcomeActivity.this, ChangePasswordActivity.class);
                                            intent.putExtra("phone_number", phoneNumber);
                                            intent.putExtra("caller", "forgot");

                                            if (original_code.equals(verification_edit.getText().toString())) {
                                                startActivity(intent);
                                                finish();

                                            } else {
                                                Toast.makeText(WelcomeActivity.this, "کد وارد شده معتبر نیست", Toast.LENGTH_LONG).show();
                                            }

                                        }
                                    });
                                } else {
                                    Toast.makeText(WelcomeActivity.this, "شماره وارد شده معتبر نیست", Toast.LENGTH_LONG).show();

                                }
                            }

                        });
                    }

                });
            }
        });


        btnSignUp = (Button) findViewById(R.id.button_sign_up);
        btnSignUp.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {

                welcome_layout.setVisibility(View.GONE);
                sign_up_layout.setVisibility(View.VISIBLE);

                ph_number_sign_up.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if (!hasFocus) {
                            imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    }
                });

                btnSignUp2 = (Button) findViewById(R.id.button_sign_up2);
                btnSignUp2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                        phoneNumber = ph_number_sign_up.getText().toString();

                        if (isNumberValid(phoneNumber)) {
                            sign_up_layout.setVisibility(View.GONE);
                            verification_layout.setVisibility(View.VISIBLE);
                            resend_code = (TextView) findViewById(R.id.resend_code);
                            verify = (Button) findViewById(R.id.button_verify);
                            verification_edit = (EditText) findViewById(R.id.edit_code_verify);

                            Retrofit sms_retrofit = new Retrofit.Builder().baseUrl("http://elmirayafteh.ir/sciwebservice/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            SMSPanel smsService = sms_retrofit.create(SMSPanel.class);

                            original_code = UUID.randomUUID().toString().substring(0, 5);

                            smsService.SendSms(phoneNumber, original_code).enqueue(new Callback<SMS>() {
                                @Override
                                public void onResponse(Call<SMS> call, Response<SMS> response) {

                                }

                                @Override
                                public void onFailure(Call<SMS> call, Throwable t) {

                                }
                            });

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    resend_code.setVisibility(View.VISIBLE);
                                }
                            }, 60000);

                            resend_code.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View view) {
                                    Retrofit sms_retrofit = new Retrofit.Builder().baseUrl("http://elmirayafteh.ir/sciwebservice/")
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();

                                    SMSPanel smsService = sms_retrofit.create(SMSPanel.class);
                                    original_code = UUID.randomUUID().toString().substring(0, 4);
                                    resend_code.setVisibility(View.GONE);

                                    smsService.SendSms(phoneNumber, original_code).enqueue(new Callback<SMS>() {
                                        @Override
                                        public void onResponse(Call<SMS> call, Response<SMS> response) {
                                        }

                                        @Override
                                        public void onFailure(Call<SMS> call, Throwable t) {

                                        }
                                    });

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            resend_code.setVisibility(View.VISIBLE);
                                        }
                                    }, 60000);
                                }
                            });

                            verify.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Intent intent = new Intent(WelcomeActivity.this, SignUpActivity.class);
                                    intent.putExtra("phone_number", phoneNumber);

                                    if (original_code.equals(verification_edit.getText().toString())) {
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        Toast.makeText(WelcomeActivity.this, "کد وارد شده معتبر نیست", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });

                        } else {
                            Toast.makeText(WelcomeActivity.this, "شماره تلفن وارد شده معتبر نیست", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

        });

    }


    public static boolean isNumberValid(final String phone_number) {

        final String PASSWORD_PATTERN = "9\\d{9}";
        Pattern pattern;
        Matcher matcher;
        Boolean result;

        if (phone_number.length() == 10) {

            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(phone_number);

            result = matcher.matches();

        } else {
            result = false;
        }

        return result;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public void onBackPressed() {

        if (sign_up_layout.getVisibility() == View.VISIBLE) {
            sign_up_layout.setVisibility(View.GONE);
            welcome_layout.setVisibility(View.VISIBLE);

        } else if (sign_in_layout.getVisibility() == View.VISIBLE) {
            sign_in_layout.setVisibility(View.GONE);
            welcome_layout.setVisibility(View.VISIBLE);

        } else if (verification_layout.getVisibility() == View.VISIBLE) {
            verification_layout.setVisibility(View.GONE);
            sign_up_layout.setVisibility(View.VISIBLE);
        } else {
            welcome_layout.setVisibility(View.VISIBLE);
            super.onBackPressed();
        }

    }

}
