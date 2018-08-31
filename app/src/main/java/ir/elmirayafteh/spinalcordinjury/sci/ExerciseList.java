package ir.elmirayafteh.spinalcordinjury.sci;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ExerciseList {
    @GET("exercise.php")
    Call<List<Exercise>> getCases(@Query("sci_type") String sci_type, @Query("xp_lvl") String xp_lvl,
                              @Query("up_str") String up_str);

}
