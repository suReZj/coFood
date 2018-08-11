package sure.co_food.Fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.allen.library.SuperTextView;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.zxing.integration.android.IntentIntegrator;
import com.melnykov.fab.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import sure.co_food.Activity.MainActivity;
import sure.co_food.Activity.SearchActivity;
import sure.co_food.R;
import sure.co_food.adapter.shop_adapter;
import sure.co_food.bean.shop;
import sure.co_food.event.positionEvent;
import sure.co_food.gson.GsonShop;
import sure.co_food.gson.GsonShops;
import sure.co_food.retrofit.getShopByCity;
import sure.co_food.util.RetrofitUtil;

import static sure.co_food.Contants.Shop;
import static sure.co_food.Contants.localhost;


/**
 * Created by dell88 on 2017/12/17 0017.
 */

public class HomeFragment extends Fragment {

    private final static int CAMERA = 2;
    private Context context;
    private View view;
    private List<GsonShop> shopList = new ArrayList<>();
    private RecyclerView recyclerView;
    private shop_adapter adapter;
    private LinearLayoutManager layoutManager;
    public SuperTextView superTextView;
    private String city;
    private TextView searchTextView;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
//    private FloatingActionButton floatingActionButton;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    superTextView.setLeftString(city);
                    getShopByCity();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        context = getActivity();
        initView();
        setListener();
        return view;
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.shop_recyclerView);
//        for (int i = 0; i < 20; i++) {
//            shop shop = new shop();
//            shopList.add(shop);
//        }
//        adapter = new shop_adapter(shopList);
//        recyclerView.setAdapter(adapter);
//        layoutManager = new LinearLayoutManager(view.getContext());
//        recyclerView.setLayoutManager(layoutManager);

        superTextView = view.findViewById(R.id.superTextView_top);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getPosition();
        }
        searchTextView=view.findViewById(R.id.home_fragment_search);
    }

    private void getPosition() {
        mLocationClient = new LocationClient(context);
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true
        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        mLocationClient.start();
    }


    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            Message msg = Message.obtain();
            msg.what = 1;
            handler.sendMessage(msg);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    // This method will be called when a MessageEvent is posted
    @Subscribe
    public void onMessageEvent(positionEvent event) {
        getPosition();
    }

    private void setListener() {
        superTextView.setRightImageViewClickListener(new SuperTextView.OnRightImageViewClickListener() {
            @Override
            public void onClickListener(ImageView imageView) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA}, CAMERA);
                } else {
                    IntentIntegrator integrator = new IntentIntegrator(getActivity());
                    integrator.setOrientationLocked(false);
                    integrator.initiateScan();
                    }
              }
            }
        );
        searchTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getShopByCity(){
        Retrofit retrofit = RetrofitUtil.getRetrofit(localhost + Shop);
        getShopByCity getShopByCity=retrofit.create(getShopByCity.class);
        getShopByCity.getShop(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GsonShops>() {
                    private Disposable disposable;
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(GsonShops gsonShops) {
                        shopList=gsonShops.getList();
                        adapter = new shop_adapter(shopList);
                        recyclerView.setAdapter(adapter);
                        layoutManager = new LinearLayoutManager(view.getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        Log.e("1",gsonShops.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        disposable.dispose();
                        Log.e("1",e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onResume() {
//        getShopByCity();
        super.onResume();
    }
}
