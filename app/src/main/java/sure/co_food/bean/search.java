package sure.co_food.bean;

import android.util.Log;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import sure.co_food.gson.GsonGood;
import sure.co_food.gson.GsonShop;
import sure.co_food.retrofit.getShopById;
import sure.co_food.util.RetrofitUtil;

import static sure.co_food.Contants.Shop;
import static sure.co_food.Contants.localhost;

/**
 * Created by dell88 on 2018/2/14 0014.
 */

public class search {
    private String shopphone;
    private List<GsonGood> list;

    public search(String shopphone, List<GsonGood> list) {
        this.shopphone = shopphone;
        this.list = list;
    }

    public search() {
    }

    public String getShopphone() {
        return shopphone;
    }

    public void setShopphone(String shopphone) {
        this.shopphone = shopphone;
    }

    public List<GsonGood> getList() {
        return list;
    }

    public void setList(List<GsonGood> list) {
        this.list = list;
    }
}
