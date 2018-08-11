package sure.co_food.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import sure.co_food.R;
import sure.co_food.adapter.shop_adapter;
import sure.co_food.gson.GsonShop;
import sure.co_food.retrofit.getShopById;
import sure.co_food.util.RetrofitUtil;
import sure.co_food.util.UserUtil;

import static sure.co_food.Contants.Shop;
import static sure.co_food.Contants.localhost;

public class StarActivity extends BaseActivity {
    private Toolbar activity_star_toolbar;
    private RecyclerView activity_star_rv;
    private shop_adapter adapter;
    private List<GsonShop> list=new ArrayList<>();
    private int length= UserUtil.stars.getList().size();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_star);
        activity_star_toolbar=findViewById(R.id.activity_star_toolbar);
        activity_star_rv=findViewById(R.id.activity_star_rv);

        activity_star_toolbar.setTitle("我的收藏");
        activity_star_toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(activity_star_toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if(UserUtil.stars.getList().size()>0){
            showShop(UserUtil.stars.getList().get(0).getShopphone(),0);
        }
    }

    @Override
    protected void setListener() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showShop(final String shopphone, final int pos){
        System.out.println(shopphone);
        Retrofit retrofit= RetrofitUtil.getRetrofit(localhost+Shop);
        getShopById getShopById=retrofit.create(getShopById.class);
        getShopById.getShopById(shopphone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GsonShop>() {
                    private Disposable disposable;
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(GsonShop gsonShop) {
                        list.add(gsonShop);
                        if(pos<length-1){
                            showShop(UserUtil.stars.getList().get(pos+1).getShopphone(),pos+1);
                        }else {
                            adapter=new shop_adapter(list);
                            activity_star_rv.setAdapter(adapter);
                            activity_star_rv.setLayoutManager(new LinearLayoutManager(StarActivity.this));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("showshop",e.toString());
                        disposable.dispose();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
