package sure.co_food.retrofit;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;
import sure.co_food.gson.GsonStars;

/**
 * Created by dell88 on 2018/2/12 0012.
 */

public interface deleteStar {
    @POST("deleteStar")
    Observable<GsonStars> startShop(@Query("userphone") String userphone, @Query("shopphone") String shopphone);
}
