package sure.co_food.retrofit;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import sure.co_food.gson.GsonUsers;

/**
 * Created by dell88 on 2018/2/8 0008.
 */

public interface registerInterface {
//    @POST("registerUser")
//    Observable<GsonUsers> registerUser(@Query("userphone") String userphone,
//                                  @Query("username") String username,
//                                  @Query("usercity") String usercity,
//                                  @Query("userpassword") String userpassword,
//                                  @Query("isshop") boolean isshop,
//                                  @Query("userlocation") String userlocetion
//    );
    @POST("registerUser")
    Observable<GsonUsers> registerUser(@QueryMap Map<String,String> map);
}
