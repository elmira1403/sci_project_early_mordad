package com.example.elmira.sci;


import android.content.SharedPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyPreferences {

    Retrofit retrofit;
    String retrofit_result;
    SharedPreferences sharedPreferences;

    private RetroResultListener mListener;

    public interface RetroResultListener {
        void onDataLoaded(String result);

    }

    public MyPreferences(SharedPreferences sharedPreferences){
        this.sharedPreferences=sharedPreferences;
    }

    public void setListener(RetroResultListener listener) {
        mListener = listener;
    }

    public SharedPreferences saveOnDevice(UserInfo mUserInfo) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("phone_number", mUserInfo.getPhone());
        editor.putString("password", mUserInfo.password);
        editor.putString("email", mUserInfo.email);
        editor.putString("birthday", mUserInfo.birthday);
        editor.putString("sex", mUserInfo.sex);
        editor.putString("sci_type", mUserInfo.sci_type);
        editor.putString("experience_level", mUserInfo.experience_level);
        editor.putString("upper_strength", mUserInfo.upper_strength);
        editor.putString("height", mUserInfo.height);
        editor.putString("fitness", mUserInfo.fitness);
        editor.putString("h_real", mUserInfo.h_real);
        editor.putString("w_real", mUserInfo.w_real);

        editor.apply();

        return sharedPreferences;
    }

    public void saveOnServer() {

        String phone_number = sharedPreferences.getString("phone_number", null);
        String password = sharedPreferences.getString("password", null);
        String email = sharedPreferences.getString("email", null);
        String birthday = sharedPreferences.getString("birthday", null);
        String sex = sharedPreferences.getString("sex", null);
        String sci_type = sharedPreferences.getString("sci_type", null);
        String experience_level = sharedPreferences.getString("experience_level", null);
        String upper_strength = sharedPreferences.getString("upper_strength", null);
        String h_real = sharedPreferences.getString("h_real", null);
        String w_real = sharedPreferences.getString("w_real", null);
        String height = sharedPreferences.getString("height", null);
        String fitness = sharedPreferences.getString("fitness", null);


        retrofit = new Retrofit.Builder()
                .baseUrl("http://elmirayafteh.ir/sciwebservice/").addConverterFactory(GsonConverterFactory.create())
                .build();

        RegisterUser uService = retrofit.create(RegisterUser.class);

        uService.registerUser(phone_number, password, email, birthday, sex, sci_type, experience_level,
                upper_strength, h_real, w_real, height, fitness).enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    retrofit_result = response.body();

                    if (mListener != null)
                        mListener.onDataLoaded(retrofit_result);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                retrofit_result = "false";

                if (mListener != null)
                    mListener.onDataLoaded(retrofit_result);
            }

        });


    }

    public Boolean updateOnServer() {

        String phone_number = sharedPreferences.getString("phone_number", null);
        String password = sharedPreferences.getString("password", null);
        String email = sharedPreferences.getString("email", null);
        String birthday = sharedPreferences.getString("birthday", null);
        String sex = sharedPreferences.getString("sex", null);
        String sci_type = sharedPreferences.getString("sci_type", null);
        String experience_level = sharedPreferences.getString("experience_level", null);
        String upper_strength = sharedPreferences.getString("upper_strength", null);
        String h_real = sharedPreferences.getString("h_real", null);
        String w_real = sharedPreferences.getString("w_real", null);
        String height = sharedPreferences.getString("height", null);
        String fitness = sharedPreferences.getString("fitness", null);


        retrofit = new Retrofit.Builder()
                .baseUrl("http://elmirayafteh.ir/sciwebservice/").addConverterFactory(GsonConverterFactory.create())
                .build();

        UpdateUser uService = retrofit.create(UpdateUser.class);

        uService.updateUser(phone_number, password, email, birthday, sex, sci_type, experience_level,
                upper_strength, h_real, w_real, height, fitness).enqueue(new Callback<String>() {


            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    retrofit_result = response.body();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                retrofit_result = "false";
            }

        });

        return Boolean.parseBoolean(retrofit_result);
    }

}
