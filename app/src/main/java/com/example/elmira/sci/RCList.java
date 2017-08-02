package com.example.elmira.sci;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RCList {
    @GET("firsttest.php")
    Call<List<Case>> getCases(@Query("sci_type") String sci_type, @Query("xp_lvl") String xp_lvl,
                              @Query("fitness") String fitness, @Query("height") String height,
                              @Query("up_str") String up_str, @Query("sex") String sex);
}
