package sure.co_food.retrofit;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;
import sure.co_food.gson.GsonGoods;

/**
 * Created by dell88 on 2018/2/9 0009.
 */

public interface getGoods {
    @POST("getShopGoods")
    Observable<GsonGoods> getGoods(@Query("shopphone") String shopphone);
}
