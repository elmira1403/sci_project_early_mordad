package ir.elmirayafteh.spinalcordinjury.sci;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UpdateUser {
    @GET("updateUser.php")
    Call<String> updateUser(@Query("phone_number") String phone_number,
                            @Query("email") String email,
                            @Query("birthday") String birthday,
                            @Query("gender") String gender,
                            @Query("sci_type") String sci_type,
                            @Query("experience_level") String experience_level,
                            @Query("upper_strength") String upper_strength,
                            @Query("h_real") String h_real,
                            @Query("w_real") String w_real,
                            @Query("height") String height,
                            @Query("fitness") String fitness);

}
