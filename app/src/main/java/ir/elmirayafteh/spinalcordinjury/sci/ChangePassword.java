package ir.elmirayafteh.spinalcordinjury.sci;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ChangePassword {
    @GET("changePassword.php")
    Call<String> changePassword(@Query("phone_number") String phone_number,
                                @Query("old_password") String old_password,
                                @Query("new_password") String new_password);

}
