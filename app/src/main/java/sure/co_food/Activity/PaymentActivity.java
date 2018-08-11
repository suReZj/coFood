package sure.co_food.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.github.lguipeng.library.animcheckbox.AnimCheckBox;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import sure.co_food.MyDecoration;
import sure.co_food.R;
import sure.co_food.adapter.payment_adapter;
import sure.co_food.bean.goods;
import sure.co_food.bean.shop;
import sure.co_food.gson.GsonMsg;
import sure.co_food.gson.GsonOrder;
import sure.co_food.gson.GsonShop;
import sure.co_food.retrofit.sumbitOrder;
import sure.co_food.util.RetrofitUtil;
import sure.co_food.util.UserUtil;

import static sure.co_food.Contants.localhost;
import static sure.co_food.Contants.orders;
import static sure.co_food.Contants.price;


public class PaymentActivity extends BaseActivity {
    private Toolbar toolbar;
    private TextView payment_location;
    private AnimCheckBox payment_online_btn;
    private AnimCheckBox payment_after_btn;
    private SuperTextView payment_shop_name;
    private RecyclerView payment_rv;
    private SuperTextView payment_distribution;
    private SuperTextView payment_total_price;
    private EditText payment_edit;
    private TextView payment_bottom_total_price;
    private TextView payment_sure_btn;
    private GsonShop shop;
    private List<goods> goodsList = new ArrayList<>();
    private double totalPrice;
    private payment_adapter payment_adapter;
    private AlertDialog dialog;
    Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        shop = (GsonShop) getIntent().getSerializableExtra("shop");
        shop shops = (shop)getIntent().getSerializableExtra("goodsList");
        goodsList=shops.getSelectedGoods();
        totalPrice = totalPrice + shop.getShopdistribution();
        payment_adapter = new payment_adapter(goodsList);
        for (int i = 0; i < goodsList.size(); i++) {
            totalPrice = totalPrice + goodsList.get(i).getPrice() * goodsList.get(i).getSelectSum();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_payment);
        payment_location = findViewById(R.id.payment_location);
        payment_location.setText(UserUtil.gsonUsers.getUserlocation());
        payment_online_btn = findViewById(R.id.payment_online_btn);
        payment_after_btn = findViewById(R.id.payment_after_btn);
        payment_shop_name = findViewById(R.id.payment_shop_name);
        payment_rv = findViewById(R.id.payment_rv);
        payment_distribution = findViewById(R.id.payment_distribution);
        payment_total_price = findViewById(R.id.payment_total_price);
        payment_edit = findViewById(R.id.payment_edit);
        payment_bottom_total_price = findViewById(R.id.payment_bottom_total_price);
        payment_sure_btn = findViewById(R.id.payment_sure_btn);

        toolbar = findViewById(R.id.payment_toolbar);
        toolbar.setTitle("订单结算");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        dialog = new SpotsDialog(PaymentActivity.this, "正在下单...");

        setData();
    }

    private void setData() {
        if (shop.getShopname() != null) {
            payment_shop_name.setCenterString(shop.getShopname());
        }
        payment_distribution.setRightString(price + shop.getShopdistribution());
        payment_total_price.setRightString(price + totalPrice);
        payment_bottom_total_price.setText(price + totalPrice);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        payment_rv.setAdapter(payment_adapter);
        payment_rv.setLayoutManager(layoutManager);
        payment_rv.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST, R.drawable.divider_right_rv));
    }

    @Override
    protected void setListener() {
        payment_online_btn.setOnCheckedChangeListener(new AnimCheckBox.OnCheckedChangeListener() {
            @Override
            public void onChange(AnimCheckBox animCheckBox, boolean b) {
                if (payment_online_btn.isChecked()) {
                    payment_after_btn.setChecked(false, true);
                } else {
                    payment_after_btn.setChecked(true, true);
                }
            }
        });
        payment_after_btn.setOnCheckedChangeListener(new AnimCheckBox.OnCheckedChangeListener() {
            @Override
            public void onChange(AnimCheckBox animCheckBox, boolean b) {
                if (payment_after_btn.isChecked()) {
                    payment_online_btn.setChecked(false, true);
                } else {
                    payment_online_btn.setChecked(true, true);
                }
            }
        });
        payment_sure_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    dialog.show();
                    handler.postDelayed(runnable, 2000);//每两秒执行一次runnable.
            }
        });
    }


    private void sumbitOrder(){
        String payWay="货到付款";
        if(payment_online_btn.isChecked()){
            payWay="在线付款";
        }
        int goodSum=0;
        String goodList="";
        String eachgoodsum="";
        for(int i=0;i<goodsList.size()-1;i++){
            goodList=goodList+goodsList.get(i).getId()+",";
            eachgoodsum=eachgoodsum+goodsList.get(i).getSelectSum()+",";
            goodSum=goodSum+goodsList.get(i).getSelectSum();
        }
        goodList=goodList+goodsList.get(goodsList.size()-1).getId();
        eachgoodsum=eachgoodsum+goodsList.get(goodsList.size()-1).getSelectSum();
        goodSum=goodSum+goodsList.get(goodsList.size()-1).getSelectSum();

        String notice=String.valueOf(payment_edit.getText());

        Retrofit retrofit = RetrofitUtil.getRetrofit(localhost + orders);
        sumbitOrder sumbitOrder=retrofit.create(sumbitOrder.class);
        sumbitOrder.sumbitOrder(UserUtil.gsonUsers.getUserphone(),shop.getShopphone(),payWay,goodSum,totalPrice,"订单准备中",shop.getShopname(),goodList,eachgoodsum,notice,UserUtil.gsonUsers.getUserlocation(),shop.getShopimagepath())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GsonMsg>() {
                    private Disposable disposable;
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(GsonMsg gsonOrder) {
                        dialog.dismiss();
                        UserUtil.setList(UserUtil.gsonUsers.getUserphone());
                        Intent intent=new Intent();
                        setResult(1,intent);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        disposable.dispose();
                        dialog.dismiss();
                        Toast.makeText(PaymentActivity.this,"下单失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //要做的事情
            sumbitOrder();
        }
    };

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        setResult(2,intent);
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
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
}
