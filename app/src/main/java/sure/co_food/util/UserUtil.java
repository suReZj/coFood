package sure.co_food.util;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import sure.co_food.gson.GsonOrderList;
import sure.co_food.gson.GsonStars;
import sure.co_food.gson.GsonUsers;
import sure.co_food.retrofit.getOrderList;
import sure.co_food.retrofit.getStarList;

import static sure.co_food.Contants.localhost;
import static sure.co_food.Contants.orders;
import static sure.co_food.Contants.star;

/**
 * Created by dell88 on 2018/2/10 0010.
 */

public class UserUtil {
    public static GsonUsers gsonUsers;
    public static GsonOrderList list;
    public static GsonStars stars;
    public static void setList(String userPhone){
        Retrofit retrofit = RetrofitUtil.getRetrofit(localhost + orders);
        getOrderList getOrderList=retrofit.create(getOrderList.class);
        getOrderList.getList(userPhone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GsonOrderList>() {
                    private Disposable disposable;
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(GsonOrderList gsonOrderList) {
                        Log.e("order","1");
                        UserUtil.list=gsonOrderList;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("order",e.toString());
                        disposable.dispose();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public static void getStar(){
        Retrofit retrofit = RetrofitUtil.getRetrofit(localhost + star);
        getStarList getStarList=retrofit.create(getStarList.class);
        getStarList.getStarList(gsonUsers.getUserphone())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GsonStars>() {
                    private Disposable disposable;
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(GsonStars gsonStars) {
                        stars=gsonStars;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("star",e.toString());
                        disposable.dispose();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
