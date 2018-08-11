package sure.co_food.retrofit;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;
import sure.co_food.gson.GsonOrder;

/**
 * Created by dell88 on 2018/2/12 0012.
 */

public interface sureOrder {
    @POST("sureOrder")
    Observable<GsonOrder> sureOrder(@Query("id") Integer id, @Query("userphone") String userphone);
}
