package sure.co_food.retrofit;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;
import sure.co_food.gson.GsonOrderList;

/**
 * Created by dell88 on 2018/2/11 0011.
 */

public interface getOrderList {
    @POST("getOrder")
    Observable<GsonOrderList> getList(@Query("userphone") String userphone);
}
