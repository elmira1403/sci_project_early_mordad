package com.example.elmira.sci;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RegisterUser {
    @GET("registerUser.php")
    Call<String> registerUser(@Query("phone_number") String phone_number,
                               @Query("password") String password,
                               @Query("email") String email,
                               @Query("birthday") String birthday,
                               @Query("sex") String sex,
                               @Query("sci_type") String sci_type,
                               @Query("experience_level") String experience_level,
                               @Query("upper_strength") String upper_strength,
                               @Query("h_real") String h_real,
                               @Query("w_real") String w_real,
                               @Query("height") String height,
                               @Query("fitness") String fitness);

}
