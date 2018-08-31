package ir.elmirayafteh.spinalcordinjury.sci;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SMSPanel {

    @GET("sms.php")
    Call<SMS> SendSms(@Query("to") String to,
                      @Query("code") String code);

}
