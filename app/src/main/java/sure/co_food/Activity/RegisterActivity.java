package sure.co_food.Activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import sure.co_food.R;
import sure.co_food.gson.GsonUsers;
import sure.co_food.retrofit.registerInterface;
import sure.co_food.util.RetrofitUtil;

import static sure.co_food.Contants.localhost;
import static sure.co_food.Contants.user;

public class RegisterActivity extends BaseActivity {
    private final static int ACCESS_FINE_LOCATION = 1;
    private FloatingActionButton register_fab;
    private CardView register_cardView;
    private Toolbar register_toolbar;
    private TextView register_go;
    private AlertDialog dialog;
    private EditText register_username;
    private EditText register_nickname;
    private EditText register_password;
    private EditText register_location;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private String usercity;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ShowEnterAnimation();
        setContentView(R.layout.activity_register);
        register_username = findViewById(R.id.register_username);
        register_nickname = findViewById(R.id.register_nickname);
        register_password = findViewById(R.id.register_password);
        register_location = findViewById(R.id.register_location);
        dialog = new SpotsDialog(RegisterActivity.this, "正在注册...");
        register_go = findViewById(R.id.register_go);
        register_fab = findViewById(R.id.register_fab);
        register_cardView = findViewById(R.id.register_cardView);
        register_toolbar = findViewById(R.id.register_toolbar);
        register_toolbar.setTitle("注册");
        register_toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(register_toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getPosition();
        requestPermission();
    }

    @Override
    protected void setListener() {
        register_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateRevealClose();
            }
        });
        register_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (String.valueOf(register_username.getText()).length() == 0 || String.valueOf(register_nickname.getText()).length() == 0 || String.valueOf(register_location.getText()).length() == 0 || String.valueOf(register_password).length() == 0) {
                    Toast.makeText(RegisterActivity.this, "请输入完整信息", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.show();
                    handler.postDelayed(runnable, 2000);//每两秒执行一次runnable.
                }
            }
        });
    }

    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                register_cardView.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(register_cardView, register_cardView.getWidth() / 2, 0, register_fab.getWidth() / 2, register_cardView.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                register_cardView.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(register_cardView, register_cardView.getWidth() / 2, 0, register_cardView.getHeight(), register_fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                register_cardView.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                register_fab.setImageResource(R.drawable.plus);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @Override
    public void onBackPressed() {
        animateRevealClose();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                animateRevealClose();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //要做的事情
            registerUser();
        }
    };

    public void registerUser() {
        Retrofit retrofit = RetrofitUtil.getRetrofit(localhost + user);
        registerInterface registerInterface = retrofit.create(registerInterface.class);
        Map<String, String> map = new HashMap<>();
        map.put("userphone", String.valueOf(register_username.getText()));
        map.put("username", String.valueOf(register_nickname.getText()));
        map.put("usercity", usercity);
        map.put("userpassword", String.valueOf(register_password.getText()));
        map.put("isshop", "0");
        map.put("userlocation", String.valueOf(register_location.getText()));
        Log.e("11",String.valueOf(register_location.getText()));
//        registerInterface.registerUser(String.valueOf(register_username.getText()),String.valueOf(register_nickname.getText()),usercity,String.valueOf(register_password.getText()),false,String.valueOf(register_location.getText()))
        registerInterface.registerUser(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GsonUsers>() {
                    private Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(GsonUsers gsonUsers) {
                        if (gsonUsers.getUserphone().equals(String.valueOf(register_username.getText()))) {
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
//                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//                            startActivity(intent);
//                            finish();
                            animateRevealClose();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(RegisterActivity.this, "该用户已存在", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        Log.e("ee", e.toString());
                        handler.removeCallbacks(runnable);
                        disposable.dispose();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getPosition() {
        mLocationClient = new LocationClient(getApplicationContext());
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
            usercity = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
        }
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ACCESS_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(RegisterActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
