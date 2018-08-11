package sure.co_food.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.transition.Explode;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import sure.co_food.Fragment.HomeFragment;
import sure.co_food.Fragment.MyFragment;
import sure.co_food.Fragment.OrderFragment;
import sure.co_food.R;
import sure.co_food.event.fnishEvent;
import sure.co_food.event.positionEvent;
import sure.co_food.gson.GsonShop;
import sure.co_food.myApplication;
import sure.co_food.retrofit.getShopById;
import sure.co_food.util.RetrofitUtil;

import static sure.co_food.Contants.Shop;
import static sure.co_food.Contants.localhost;


public class MainActivity extends BaseActivity {
    private final static int ACCESS_FINE_LOCATION = 1;
    private final static int CAMERA = 2;
    private HomeFragment homeFragment;
    private MyFragment myFragment;
    private OrderFragment orderFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction ft;
    private long backLastPressedTimestamp = 0;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    ft = fragmentManager.beginTransaction();
                    ft.show(homeFragment);
                    ft.hide(myFragment);
                    ft.hide(orderFragment);
                    ft.commit();
                    return true;
                case R.id.navigation_order:
                    ft = fragmentManager.beginTransaction();
                    ft.show(orderFragment);
                    ft.hide(homeFragment);
                    ft.hide(myFragment);
                    ft.commit();
                    return true;
                case R.id.navigation_my:
                    ft = fragmentManager.beginTransaction();
                    ft.hide(homeFragment);
                    ft.show(myFragment);
                    ft.hide(orderFragment);
                    ft.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(fnishEvent event) {
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //初始化三个fragment
        homeFragment = new HomeFragment();
        myFragment = new MyFragment();
        orderFragment = new OrderFragment();

        fragmentManager = getSupportFragmentManager();
        ft = fragmentManager.beginTransaction();

        ft.add(R.id.frameLayout, homeFragment);
        ft.add(R.id.frameLayout, myFragment);
        ft.add(R.id.frameLayout, orderFragment);

        ft.show(homeFragment);
        ft.hide(myFragment);
        ft.hide(orderFragment);
        ft.commit();

        requestPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ACCESS_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    EventBus.getDefault().post(new positionEvent());
                } else {
                    Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            case CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    IntentIntegrator integrator = new IntentIntegrator(this);
                    integrator.setOrientationLocked(false);
                    integrator.initiateScan();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
//                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                showShop(result.getContents());
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - backLastPressedTimestamp > 2 * 1000) {
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            backLastPressedTimestamp = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    public void showShop(String shopphone){
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
                        Log.e("showshop","show");
                        Intent intent=new Intent(MainActivity.this, ShopActivity.class);
                        intent.putExtra("shop",gsonShop);
                        startActivity(intent);
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
