package ir.elmirayafteh.spinalcordinjury.sci;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface User {
    @GET("userInfo.php")
    Call<UserInfo> getUserInfo(@Query("phone_number") String phone_number,
                               @Query("password") String password);

}
