package sure.co_food.Activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.CircleImageView;
import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.flipboard.bottomsheet.commons.BottomSheetFragment;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import at.markushi.ui.CircleButton;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import sure.co_food.MyDecoration;
import sure.co_food.R;
import sure.co_food.adapter.bottom_goods_adapter;
import sure.co_food.adapter.shop_rv_left_adapter;
import sure.co_food.adapter.shop_rv_right_adapter;
import sure.co_food.bean.goods;
import sure.co_food.bean.shop;
import sure.co_food.bean.type;
import sure.co_food.bean.user;
import sure.co_food.event.addGoodsEvent;
import sure.co_food.gson.GsonGood;
import sure.co_food.gson.GsonGoods;
import sure.co_food.gson.GsonShop;
import sure.co_food.gson.GsonShops;
import sure.co_food.gson.GsonStars;
import sure.co_food.gson.GsonUsers;
import sure.co_food.retrofit.deleteStar;
import sure.co_food.retrofit.getGoods;
import sure.co_food.retrofit.getShopByCity;
import sure.co_food.retrofit.starShop;
import sure.co_food.retrofit.userInterface;
import sure.co_food.util.RetrofitUtil;
import sure.co_food.util.UserUtil;


import static sure.co_food.Contants.disparity;
import static sure.co_food.Contants.good;
import static sure.co_food.Contants.imageLocalhost;
import static sure.co_food.Contants.localhost;
import static sure.co_food.Contants.price;
import static sure.co_food.Contants.star;
import static sure.co_food.Contants.stringDistribution;
import static sure.co_food.util.ShowDialog.showImageDialog;

public class ShopActivity extends BaseActivity {
    private user user;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private RecyclerView leftRecyclerView;
    private RecyclerView rightRecyclerView;
    private shop_rv_left_adapter left_adapter;
    private static shop_rv_right_adapter right_adapter;
    private List<String> typeList = new ArrayList<>();
    private List<goods> goodsList = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private boolean move = false;
    private int mIndex = 0;
    private TextView tvStickyHeaderView;
    private ConstraintLayout shop_container;
    private static ImageView view_shop_car_image;
    private PathMeasure mPathMeasure;
    /**
     * 贝塞尔曲线中间过程的点的坐标
     */
    private float[] mCurrentPosition = new float[2];

    private static int selectTotalSum = 0;
    private static TextView selectToatalSumTextView;
    private static TextView selectTotalPriceTextCiew;
    private static double selectTotalPrice;
    private RelativeLayout view_shop_relative;
    private FrameLayout view_shop_frame;
    private static List<goods> bottomGoodsList = new ArrayList<>();
    private static SuperTextView bottom_superTextView;
    private boolean bottomFlag = true;
    private static BottomFragment bottomFragment = new BottomFragment();
    private double distribution = 3;//配送费
    private static double minPrice;//起送费
    private static TextView settlement_btn;//配送按钮
    private static TextView no_settlement_btn;//不能配送按钮
    private GsonShop shop;
    private de.hdodenhof.circleimageview.CircleImageView shop_image;
    private TextView notice;
    private TextView view_shop_car_dur;
    private boolean starFlag=false;

    static Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            clearAll();
            return true;
        }
    });


    public static class BottomFragment extends BottomSheetFragment {
        private bottom_goods_adapter adapter;
        private RecyclerView recyclerView;
        private LinearLayoutManager layoutManager;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.layout_bottomsheet, container, false);
            bottom_superTextView = view.findViewById(R.id.bottom_superTextView);
            recyclerView = view.findViewById(R.id.bottom_selected_good_rv);
            layoutManager = new LinearLayoutManager(getContext());
            adapter = new bottom_goods_adapter(bottomGoodsList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(new MyDecoration(getContext(), MyDecoration.VERTICAL_LIST, R.drawable.divider_right_rv));
            setListener();
            return view;
        }

        public void setListener() {
            bottom_superTextView.setRightTvClickListener(new SuperTextView.OnRightTvClickListener() {
                @Override
                public void onClickListener() {
                    goods goods;
                    for (int i = 0; i < bottomGoodsList.size(); i++) {
                        goods = bottomGoodsList.get(i);
                        goods.setSelectSum(0);
                    }
                    bottomGoodsList.clear();
                    Message msg = Message.obtain();
                    handler.sendMessage(msg);
                }
            });
        }
    }

    public static void clearAll() {
        right_adapter.notifyDataSetChanged();
        bottomFragment.dismiss();
        selectTotalPrice = 0;
        selectTotalPriceTextCiew.setText(price + selectTotalPrice);
        selectTotalSum = 0;
        selectToatalSumTextView.setText(String.valueOf(selectTotalSum));
        selectToatalSumTextView.setVisibility(View.GONE);
        no_settlement_btn.setText(disparity + minPrice + stringDistribution);
        no_settlement_btn.setVisibility(View.VISIBLE);
        settlement_btn.setVisibility(View.GONE);
        view_shop_car_image.setImageResource(R.drawable.car_nogoods);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        user = new user();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bottomGoodsList.clear();
        selectTotalSum = 0;
        selectTotalPrice = 0;
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(addGoodsEvent event) {
        goods goods = event.getGood();
        if (event.getFlag()) {
            if (event.getBtn() != null) {
                selectTotalSum++;
                addCart(event.getBtn());
            } else {
                selectTotalSum++;
                if (selectTotalSum > 0 && selectTotalSum <= 99) {
                    selectToatalSumTextView.setText(String.valueOf(selectTotalSum));
                    selectToatalSumTextView.setVisibility(View.VISIBLE);
                }
                if (selectTotalSum > 99) {
                    selectToatalSumTextView.setText("...");
                }
                selectTotalPriceTextCiew.setText(price + selectTotalPrice);
            }
            addGood(goods);
        } else {
            reduceGood(goods);
        }
    }


    private void addGood(goods goods) {
        selectTotalPrice = selectTotalPrice + goods.getPrice();
        right_adapter.notifyDataSetChanged();
        int position = -1;
        for (int i = 0; i < bottomGoodsList.size(); i++) {
            if (goods.getId() == bottomGoodsList.get(i).getId()) {
                position = i;
                break;
            }
        }
        if (position == -1) {
            bottomGoodsList.add(goods);
        }
        if (selectTotalPrice >= minPrice) {
            settlement_btn.setVisibility(View.VISIBLE);
            no_settlement_btn.setVisibility(View.GONE);
        } else {
            settlement_btn.setVisibility(View.GONE);
            no_settlement_btn.setVisibility(View.VISIBLE);
            no_settlement_btn.setText(disparity + (minPrice - selectTotalPrice) + stringDistribution);
        }
    }

    private void reduceGood(goods goods) {
        selectTotalSum--;
        right_adapter.notifyDataSetChanged();
        for (int i = 0; i < bottomGoodsList.size(); i++) {
            if (goods.getId() == bottomGoodsList.get(i).getId()) {
                if (bottomGoodsList.get(i).getSelectSum() <= 0) {
                    bottomGoodsList.remove(i);
                }
                break;
            }
        }
        if (bottomGoodsList.size() == 0 && bottomFragment.isVisible()) {
            bottomFragment.dismiss();
        }
        if (selectTotalSum <= 0) {
            selectToatalSumTextView.setVisibility(View.GONE);
            view_shop_car_image.setImageResource(R.drawable.car_nogoods);
        } else if (selectTotalSum > 99) {
            selectToatalSumTextView.setText("...");
        } else {
            selectToatalSumTextView.setText(String.valueOf(selectTotalSum));
        }
        selectTotalPrice = selectTotalPrice - goods.getPrice();
        selectTotalPriceTextCiew.setText(price + selectTotalPrice);

        if (selectTotalPrice >= minPrice) {
            settlement_btn.setVisibility(View.VISIBLE);
            no_settlement_btn.setVisibility(View.GONE);
        } else {
            settlement_btn.setVisibility(View.GONE);
            no_settlement_btn.setVisibility(View.VISIBLE);
            no_settlement_btn.setText(disparity + (minPrice - selectTotalPrice) + stringDistribution);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        shop = (GsonShop) getIntent().getSerializableExtra("shop");
        minPrice=shop.getShopminprice();
        setContentView(R.layout.activity_shop);
        view_shop_car_dur=findViewById(R.id.view_shop_car_dur);
        view_shop_car_dur.setText("另需配送费¥"+shop.getShopdistribution());
        shop_image = findViewById(R.id.shop_image);
        Glide.with(this).load(imageLocalhost + shop.getShopimagepath()).into(shop_image);
        notice = findViewById(R.id.notice);
        notice.setText(shop.getShopnotice());
        no_settlement_btn = findViewById(R.id.no_settlement_btn);
        no_settlement_btn.setText(disparity + minPrice + stringDistribution);
        settlement_btn = findViewById(R.id.settlement_btn);
        view_shop_relative = findViewById(R.id.view_shop_relative);
        view_shop_frame = findViewById(R.id.view_shop_frame);
        selectTotalPriceTextCiew = findViewById(R.id.view_shop_total_price);
        selectToatalSumTextView = findViewById(R.id.shop_car_view_selectSum);
        view_shop_car_image = findViewById(R.id.view_shop_car_image);
        shop_container = findViewById(R.id.shop_container);
        tvStickyHeaderView = findViewById(R.id.tv_sticky_header_view);
        leftRecyclerView = findViewById(R.id.shop_rv_left);
        rightRecyclerView = findViewById(R.id.shop_rv_right);
        collapsingToolbarLayout = findViewById(R.id.shop_collapsing);
        toolbar = findViewById(R.id.shop_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(shop.getShopname());
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));

        for (int i = 0; i < goodsList.size(); i++) {
            goods good = goodsList.get(i);
            if (good.getSelectSum() > 0) {
                selectTotalSum = selectTotalSum + good.getSelectSum();
                selectTotalPrice = selectTotalPrice + good.getPrice();
                bottomGoodsList.add(good);
            }
        }
        selectTotalPriceTextCiew.setText(price + selectTotalPrice);
        if (selectTotalSum > 0 && selectTotalSum <= 99) {
            selectToatalSumTextView.setText(String.valueOf(selectTotalSum));
            selectToatalSumTextView.setVisibility(View.VISIBLE);
        }
        if (selectTotalSum > 99) {
            selectToatalSumTextView.setVisibility(View.VISIBLE);
        }
        getGoods();
    }

    protected void Listener() {
        left_adapter.setOnItemClickLitener(new shop_rv_left_adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                String type = typeList.get(position);
                for (int i = 0; i < goodsList.size(); i++) {
                    goods goods = goodsList.get(i);
                    if (goods.getType().equals(type)) {
                        smoothMoveToPosition(i);
                        right_adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        rightRecyclerView.addOnScrollListener(new RecyclerViewListener());
        right_adapter.setOnItemClickLitener(new shop_rv_right_adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        view_shop_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomFlag) {
                    if (bottomGoodsList.size() != 0) {
                        bottomFlag = false;
                        bottomFragment.show(getSupportFragmentManager(), R.id.bottomSheetLayout);
                    }
                } else {
                    bottomFragment.dismiss();
                    bottomFlag = true;
                }
            }
        });
        view_shop_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomFlag) {
                    if (bottomGoodsList.size() != 0) {
                        bottomFlag = false;
                        bottomFragment.show(getSupportFragmentManager(), R.id.bottomSheetLayout);
                    }
                } else {
                    bottomFragment.dismiss();
                    bottomFlag = true;
                }
            }
        });

        settlement_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopActivity.this, PaymentActivity.class);
                intent.putExtra("shop", shop);
                shop shops=new shop();
                shops.setSelectedGoods(bottomGoodsList);
                intent.putExtra("goodsList",shops);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            Toast.makeText(this, "下单成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "取消订单", Toast.LENGTH_SHORT).show();
        }
    }

    private void smoothMoveToPosition(int n) {
        int firstItem = layoutManager.findFirstVisibleItemPosition();
        int lastItem = layoutManager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            rightRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = rightRecyclerView.getChildAt(n - firstItem).getTop();
            rightRecyclerView.scrollBy(0, top);
        } else {
            rightRecyclerView.scrollToPosition(n);
            mIndex = n;
            move = true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_shop, menu);
        MenuItem item = menu.getItem(0);
        for(int i=0;i<UserUtil.stars.getList().size();i++){
            if(UserUtil.stars.getList().get(i).getShopphone().equals(shop.getShopphone())){
                item.setIcon(R.drawable.star);
                starFlag=true;
                break;
            }
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.share:
                showImageDialog(ShopActivity.this,shop.getShopphone());
                break;
            case R.id.star:
                if(starFlag){
                    item.setIcon(R.drawable.nostar);
                    Toast.makeText(this,"取消收藏",Toast.LENGTH_SHORT).show();
                    deleteShop();
                    starFlag=false;
                }else {
                    item.setIcon(R.drawable.star);
                    Toast.makeText(this,"收藏成功",Toast.LENGTH_SHORT).show();
                    starShop();
                    starFlag=true;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void starShop(){
        Retrofit retrofit = RetrofitUtil.getRetrofit(localhost + star);
        starShop starShop=retrofit.create(starShop.class);
        starShop.startShop(UserUtil.gsonUsers.getUserphone(),shop.getShopphone())
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
                        UserUtil.stars=gsonStars;
                    }

                    @Override
                    public void onError(Throwable e) {
                        disposable.dispose();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void deleteShop(){
        Retrofit retrofit = RetrofitUtil.getRetrofit(localhost + star);
        deleteStar deleteStar=retrofit.create(deleteStar.class);
        deleteStar.startShop(UserUtil.gsonUsers.getUserphone(),shop.getShopphone())
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
                        UserUtil.stars=gsonStars;
                    }

                    @Override
                    public void onError(Throwable e) {
                        disposable.dispose();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
                move = false;
                int n = mIndex - layoutManager.findFirstVisibleItemPosition();
                if (0 <= n && n < rightRecyclerView.getChildCount()) {
                    int top = rightRecyclerView.getChildAt(n).getTop();
                    rightRecyclerView.scrollBy(0, top);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            // 找到RecyclerView的item中，和RecyclerView的getTop 向下相距5个像素的那个item
            // (尝试2、3个像素位置都找不到，所以干脆用了5个像素)，
            // 我们根据这个item，来更新吸顶布局的内容，
            // 因为我们的StickyLayout展示的信息肯定是最上面的那个item的信息.
            View stickyInfoView = recyclerView.findChildViewUnder(tvStickyHeaderView.getMeasuredWidth() / 2, 5);
            if (stickyInfoView != null && stickyInfoView.getContentDescription() != null) {
                if (tvStickyHeaderView.getText() != stickyInfoView.getContentDescription()) {
                    left_adapter.setClickPosition(String.valueOf(stickyInfoView.getContentDescription()));
                    leftRecyclerView.scrollToPosition(left_adapter.getClickPosition());
                }
                tvStickyHeaderView.setText(String.valueOf(stickyInfoView.getContentDescription()));
            }

            // 找到固定在屏幕上方那个FakeStickyLayout下面一个像素位置的RecyclerView的item，
            // 我们根据这个item来更新假的StickyLayout要translate多少距离.
            // 并且只处理HAS_STICKY_VIEW和NONE_STICKY_VIEW这两种tag，
            // 因为第一个item的StickyLayout虽然展示，但是一定不会引起FakeStickyLayout的滚动.
            View transInfoView = recyclerView.findChildViewUnder(
                    tvStickyHeaderView.getMeasuredWidth() / 2, tvStickyHeaderView.getMeasuredHeight() + 1);

            if (transInfoView != null && transInfoView.getTag() != null) {
                int transViewStatus = (int) transInfoView.getTag();
                int dealtY = transInfoView.getTop() - tvStickyHeaderView.getMeasuredHeight();

                // 如果当前item需要展示StickyLayout，
                // 那么根据这个item的getTop和FakeStickyLayout的高度相差的距离来滚动FakeStickyLayout.
                // 这里有一处需要注意，如果这个item的getTop已经小于0，也就是滚动出了屏幕，
                // 那么我们就要把假的StickyLayout恢复原位，来覆盖住这个item对应的吸顶信息.
                if (transViewStatus == shop_rv_right_adapter.HAS_STICKY_VIEW) {
                    if (transInfoView.getTop() > 0) {
                        tvStickyHeaderView.setTranslationY(dealtY);
                    } else {
                        tvStickyHeaderView.setTranslationY(0);
                    }
                } else if (transViewStatus == shop_rv_right_adapter.NONE_STICKY_VIEW) {
                    // 如果当前item不需要展示StickyLayout，那么就不会引起FakeStickyLayout的滚动.
                    tvStickyHeaderView.setTranslationY(0);
                }
            }
            if (move) {
                move = false;
                int n = mIndex - layoutManager.findFirstVisibleItemPosition();
                if (0 <= n && n < rightRecyclerView.getChildCount()) {
                    int top = rightRecyclerView.getChildAt(n).getTop();
                    rightRecyclerView.scrollBy(0, top);
                }
            }
        }
    }


    /**
     * ★★★★★把商品添加到购物车的动画效果★★★★★
     *
     * @param
     */
    private void addCart(CircleButton btn) {
//      一、创造出执行动画的主题---imageview
        //代码new一个imageview，图片资源是上面的imageview的图片
        // (这个图片就是执行动画的图片，从开始位置出发，经过一个抛物线（贝塞尔曲线），移动到购物车里)
        final ImageView goods = new ImageView(this);
        goods.setImageDrawable(getResources().getDrawable(R.drawable.addgoods));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        shop_container.addView(goods, params);

//        二、计算动画开始/结束点的坐标的准备工作
        //得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
        int[] parentLocation = new int[2];
        shop_container.getLocationInWindow(parentLocation);

        //得到商品图片的坐标（用于计算动画开始的坐标）
        int startLoc[] = new int[2];
        btn.getLocationInWindow(startLoc);

        //得到购物车图片的坐标(用于计算动画结束后的坐标)
        int endLoc[] = new int[2];
        view_shop_car_image.getLocationInWindow(endLoc);


//        三、正式开始计算动画开始/结束的坐标
        //开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
        float startX = startLoc[0] - parentLocation[0] + btn.getWidth() / 2;
        float startY = startLoc[1] - parentLocation[1] + btn.getHeight() / 2;

        //商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的1/5
        float toX = endLoc[0] - parentLocation[0] + view_shop_car_image.getWidth() / 5;
        float toY = endLoc[1] - parentLocation[1];

//        四、计算中间动画的插值坐标（贝塞尔曲线）（其实就是用贝塞尔曲线来完成起终点的过程）
        //开始绘制贝塞尔曲线
        Path path = new Path();
        //移动到起始点（贝塞尔曲线的起点）
        path.moveTo(startX, startY);
        //使用二次萨贝尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
        path.quadTo((startX + toX) / 2, startY, toX, toY);
        //mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，
        // 如果是true，path会形成一个闭环
        mPathMeasure = new PathMeasure(path, false);

        //★★★属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(800);
        // 匀速线性插值器
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 当插值计算进行时，获取中间的每个值，
                // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
                float value = (Float) animation.getAnimatedValue();
                // ★★★★★获取当前点坐标封装到mCurrentPosition
                // boolean getPosTan(float distance, float[] pos, float[] tan) ：
                // 传入一个距离distance(0<=distance<=getLength())，然后会计算当前距
                // 离的坐标点和切线，pos会自动填充上坐标，这个方法很重要。
                mPathMeasure.getPosTan(value, mCurrentPosition, null);//mCurrentPosition此时就是中间距离点的坐标值
                // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
                goods.setTranslationX(mCurrentPosition[0]);
                goods.setTranslationY(mCurrentPosition[1]);
            }
        });
//      五、 开始执行动画
        valueAnimator.start();

//      六、动画结束后的处理
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            //当动画结束后：
            @Override
            public void onAnimationEnd(Animator animation) {
                view_shop_car_image.setImageResource(R.drawable.car);
                // 购物车的数量加1
                if (selectTotalSum > 0 && selectTotalSum <= 99) {
                    selectToatalSumTextView.setText(String.valueOf(selectTotalSum));
                    selectToatalSumTextView.setVisibility(View.VISIBLE);
                }
                if (selectTotalSum > 99) {
                    selectToatalSumTextView.setText("...");
                }
                selectTotalPriceTextCiew.setText(price + selectTotalPrice);
                // 把移动的图片imageview从父布局里移除
                shop_container.removeView(goods);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void getGoods() {
        Retrofit retrofit = RetrofitUtil.getRetrofit(localhost + good);
        getGoods getGoods = retrofit.create(getGoods.class);
        getGoods.getGoods(shop.getShopphone())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GsonGoods>() {
                    private Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(GsonGoods gsonGoods) {
                        Log.e("1", "1");
                        List<GsonGood> list = gsonGoods.getList();
                        for (int i = 0; i < list.size(); i++) {
                            goods goods = new goods(list.get(i));
                            goodsList.add(goods);
                            String type = goods.getType();
                            if (!typeList.contains(type)) {
                                typeList.add(type);
                            }
                        }
                        left_adapter = new shop_rv_left_adapter(typeList);
                        right_adapter = new shop_rv_right_adapter(goodsList);

                        layoutManager = new LinearLayoutManager(ShopActivity.this);
                        leftRecyclerView.setLayoutManager(layoutManager);
                        leftRecyclerView.setAdapter(left_adapter);
                        leftRecyclerView.addItemDecoration(new MyDecoration(ShopActivity.this, MyDecoration.VERTICAL_LIST, R.drawable.divider_left_rv));


                        layoutManager = new LinearLayoutManager(ShopActivity.this);
                        rightRecyclerView.setLayoutManager(layoutManager);
                        rightRecyclerView.setAdapter(right_adapter);
                        rightRecyclerView.addItemDecoration(new MyDecoration(ShopActivity.this, MyDecoration.VERTICAL_LIST, R.drawable.divider_right_rv));
                        Listener();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("2", e.toString());
                        disposable.dispose();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
