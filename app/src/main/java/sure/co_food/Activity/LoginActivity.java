package sure.co_food.Activity;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dmax.dialog.SpotsDialog;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import sure.co_food.R;
import sure.co_food.gson.GsonUsers;
import sure.co_food.retrofit.userInterface;
import sure.co_food.util.RetrofitUtil;
import sure.co_food.util.UserUtil;

import static sure.co_food.Contants.localhost;
import static sure.co_food.Contants.user;

public class LoginActivity extends BaseActivity {
    private final static int ACCESS_FINE_LOCATION = 1;
    private EditText login_username;
    private EditText login_password;
    private Button login_go;
    private CardView login_cardView;
    private FloatingActionButton login_fab;
    private Toolbar login_toolbar;
    private AlertDialog dialog;
    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        dialog = new SpotsDialog(LoginActivity.this, "正在登录...");
        login_username = findViewById(R.id.login_username);
        login_password = findViewById(R.id.login_password);
        login_go = findViewById(R.id.login_go);
        login_cardView = findViewById(R.id.login_cardView);
        login_fab = findViewById(R.id.login_fab);
        login_toolbar = findViewById(R.id.login_toolbar);
        login_toolbar.setTitle("登录");
        login_toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(login_toolbar);
        requestPermission();
    }

    @Override
    protected void setListener() {
        login_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (String.valueOf(login_username.getText()).length() == 0 || String.valueOf(login_password.getText()).length() == 0) {
                    if (String.valueOf(login_username.getText()).length() == 0 && String.valueOf(login_password.getText()).length() == 0) {
                        Toast.makeText(LoginActivity.this, "请输入手机号和密码", Toast.LENGTH_SHORT).show();
                    } else if (String.valueOf(login_username.getText()).length() == 0 && String.valueOf(login_password.getText()).length() > 0) {
                        Toast.makeText(LoginActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dialog.show();
                    UserUtil.setList(String.valueOf(login_username.getText()));
                    handler.postDelayed(runnable, 2000);//每两秒执行一次runnable.
                }
            }
        });
        login_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, login_fab, login_fab.getTransitionName());
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class), options.toBundle());
            }
        });

    }

    private void getUserData() {
        Retrofit retrofit = RetrofitUtil.getRetrofit(localhost + user);
        userInterface userInterface = retrofit.create(userInterface.class);
        userInterface.getUser(String.valueOf(login_username.getText()))
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
                        if (gsonUsers.getUserpassword().equals(String.valueOf(login_password.getText()))) {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            UserUtil.gsonUsers=gsonUsers;
//                            UserUtil.setList(gsonUsers.getUserphone());
                            UserUtil.getStar();
                            dialog.dismiss();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            handler.removeCallbacks(runnable);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(LoginActivity.this, "该用户不存在", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        handler.removeCallbacks(runnable);
                        disposable.dispose();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        login_fab.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        login_fab.setVisibility(View.VISIBLE);
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
            getUserData();
        }
    };

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ACCESS_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(LoginActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
