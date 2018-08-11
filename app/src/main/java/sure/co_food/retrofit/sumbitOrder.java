package sure.co_food.retrofit;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;
import sure.co_food.gson.GsonMsg;

/**
 * Created by dell88 on 2018/2/11 0011.
 */

public interface sumbitOrder {
    @POST("sumbitOrder")
    Observable<GsonMsg> sumbitOrder(@Query("userphone") String userphone,
                                    @Query("shopphone") String shopphone,
                                    @Query("payway") String payway,
                                    @Query("goodsum") int goodsum,
                                    @Query("totalprice") double totalprice,
                                    @Query("orderstate") String orderstate,
                                    @Query("shopname") String shopname,
                                    @Query("goodlist") String goodlist,
                                    @Query("eachgoodsum") String eachgoodsum,
                                    @Query("usernotice") String usernotice,
                                    @Query("userlocation") String userlocation,
                                    @Query("shopimagepath") String shopimagepath);
}
