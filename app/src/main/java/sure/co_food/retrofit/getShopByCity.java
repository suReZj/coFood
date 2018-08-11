package sure.co_food.retrofit;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;
import sure.co_food.gson.GsonShops;

/**
 * Created by dell88 on 2018/2/8 0008.
 */

public interface getShopByCity {
    @POST("getShopByCity")
    Observable<GsonShops> getShop(@Query("city") String city);
}
