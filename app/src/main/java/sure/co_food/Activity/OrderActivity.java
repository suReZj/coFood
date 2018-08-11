package sure.co_food.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import sure.co_food.MyDecoration;
import sure.co_food.R;
import sure.co_food.adapter.order_info_adapter;
import sure.co_food.gson.GsonOrder;

import static sure.co_food.Contants.imageLocalhost;

public class OrderActivity extends BaseActivity {
    SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    private GsonOrder order;
    private Toolbar activity_order_toolbar;
    private SuperTextView activity_order_name;
    private SuperTextView activity_order_price;
    private SuperTextView activity_order_total_price;
    private SuperTextView activity_order_location;
    private SuperTextView activity_order_payway;
    private SuperTextView activity_order_time;
    private RecyclerView activity_order_recyclerView;
    private order_info_adapter adapter;
    private TextView activity_order_notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent=getIntent();
        order=(GsonOrder) intent.getSerializableExtra("order");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order);
        activity_order_toolbar=findViewById(R.id.activity_order_toolbar);
        activity_order_toolbar.setTitle("订单详情");
        activity_order_toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(activity_order_toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        activity_order_name=findViewById(R.id.activity_order_name);
        activity_order_name.setLeftString(order.getShopname());
        activity_order_name.setRightString(order.getOrderstate());
        Glide.with(this).load(imageLocalhost+order.getShopimagepath()).into(activity_order_name.getLeftIconIV());

        activity_order_price=findViewById(R.id.activity_order_price);
        activity_order_price.setRightString("共" + order.getGoodsum() + "件商品，实付¥" + order.getTotalprice() + "元");

        activity_order_total_price=findViewById(R.id.activity_order_total_price);
        activity_order_total_price.setRightString("¥"+order.getTotalprice());

        activity_order_location=findViewById(R.id.activity_order_location);
        activity_order_location.setRightString(order.getUserlocation());

        activity_order_payway=findViewById(R.id.activity_order_payway);
        activity_order_payway.setRightString(order.getPayway());

        activity_order_time=findViewById(R.id.activity_order_time);
        Date date=new Date(order.getOrdertime());
        activity_order_time.setRightString(format.format(date).toString());

        activity_order_recyclerView=findViewById(R.id.activity_order_recyclerView);
        String str[] = order.getGoodlist().split(",");
        String str1[]=order.getEachgoodsum().split(",");
        int array[] = new int[str.length];
        int selectArray[] =new int[str1.length];
        for (int i = 0; i < str.length; i++) {
            array[i] = Integer.parseInt(str[i]);
        }
        for (int i = 0; i < str1.length; i++) {
            selectArray[i] = Integer.parseInt(str1[i]);
        }
        adapter=new order_info_adapter(array,selectArray,order.getShopphone());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        activity_order_recyclerView.setAdapter(adapter);
        activity_order_recyclerView.setLayoutManager(layoutManager);
        activity_order_recyclerView.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST, R.drawable.divider_right_rv));

        activity_order_notice=findViewById(R.id.activity_order_notice);
        if(order.getUsernotice()==null){
            activity_order_notice.setText("无");
        }else {
            activity_order_notice.setText(order.getUsernotice());
        }
    }

    @Override
    protected void setListener() {
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
