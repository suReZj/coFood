package sure.co_food.retrofit;


import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;
import sure.co_food.gson.GsonUsers;

/**
 * Created by dell88 on 2018/2/7 0007.
 */

public interface userInterface {
    @POST("getUser")
    Observable<GsonUsers> getUser(@Query("userphone") String userphone);
}
