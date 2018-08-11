package sure.co_food.retrofit;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;
import sure.co_food.gson.GsonSearch;

/**
 * Created by dell88 on 2018/2/14 0014.
 */

public interface searchGood {
    @POST("search")
    Observable<GsonSearch> searchGood(@Query("search") String search);
}
