package sure.co_food.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;

import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import sure.co_food.Activity.LoginActivity;
import sure.co_food.Activity.OrderActivity;
import sure.co_food.R;
import sure.co_food.bean.order;
import sure.co_food.gson.GsonGood;
import sure.co_food.gson.GsonGoods;
import sure.co_food.gson.GsonOrder;
import sure.co_food.retrofit.getGoodsById;
import sure.co_food.retrofit.sureOrder;
import sure.co_food.util.RetrofitUtil;
import sure.co_food.util.UserUtil;

import static sure.co_food.Contants.good;
import static sure.co_food.Contants.imageLocalhost;
import static sure.co_food.Contants.localhost;
import static sure.co_food.Contants.orders;

/**
 * Created by dell88 on 2017/12/24 0024.
 */

public class order_adapter extends RecyclerView.Adapter<order_adapter.ViewHolder> {
    private Context context;
    private List<GsonOrder> orderList;
    Handler handler = new Handler();
    int id=0;
    private AlertDialog dialog;
    private int pos;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private SuperTextView item_order_name;
        private SuperTextView item_order_good1;
        private SuperTextView item_order_good2;
        private SuperTextView item_order_good3;
        private SuperTextView item_order_price;
        private Button item_order_btn;
        private CardView item_order;

        public ViewHolder(View view) {
            super(view);
            item_order_name = view.findViewById(R.id.item_order_name);
            item_order_good1 = view.findViewById(R.id.item_order_good1);
            item_order_good2 = view.findViewById(R.id.item_order_good2);
            item_order_good3 = view.findViewById(R.id.item_order_good3);
            item_order_price = view.findViewById(R.id.item_order_price);
            item_order_btn = view.findViewById(R.id.item_order_btn);
            item_order=view.findViewById(R.id.item_order);
        }
    }

    public order_adapter(List<GsonOrder> orderList) {
        this.orderList = orderList;
    }

    @Override
    public order_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        dialog = new SpotsDialog(context,"正在确认订单...");
        return holder;
    }

    @Override
    public void onBindViewHolder(order_adapter.ViewHolder holder, final int position) {
        final GsonOrder gsonOrder = orderList.get(position);
        holder.item_order_name.setLeftString(gsonOrder.getShopname());
        holder.item_order_name.setRightString(gsonOrder.getOrderstate());
        Glide.with(context).load(imageLocalhost+gsonOrder.getShopimagepath()).into(holder.item_order_name.getLeftIconIV());
        String str[] = gsonOrder.getGoodlist().split(",");
        String str1[]=gsonOrder.getEachgoodsum().split(",");
        int array[] = new int[str.length];
        int selectArray[] =new int[str1.length];
        for (int i = 0; i < str.length; i++) {
            array[i] = Integer.parseInt(str[i]);
        }
        for (int i = 0; i < str1.length; i++) {
            selectArray[i] = Integer.parseInt(str1[i]);
        }
        for(int i=0;i<array.length;i++){
            getGoodName(holder,array[i],gsonOrder.getShopphone(),i,selectArray[i]);
            if(i==2){
                i=array.length;
            }
        }
        if(array.length<=3){
            holder.item_order_price.setLeftString("");
        }
        holder.item_order_price.setRightString("共" + gsonOrder.getGoodsum() + "件商品，实付¥" + gsonOrder.getTotalprice() + "元");
        if(gsonOrder.getShopphone().equals(UserUtil.gsonUsers.getUserphone())){
            holder.item_order_btn.setVisibility(View.GONE);
        }else {
            if(gsonOrder.getOrderstate().equals("订单准备中")){
                holder.item_order_btn.setVisibility(View.VISIBLE);
            }else {
                holder.item_order_btn.setVisibility(View.GONE);
            }
        }
        holder.item_order_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, OrderActivity.class);
                intent.putExtra("order",gsonOrder);
                context.startActivity(intent);
            }
        });
        holder.item_order_good1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, OrderActivity.class);
                intent.putExtra("order",gsonOrder);
                context.startActivity(intent);
            }
        });
        holder.item_order_good2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, OrderActivity.class);
                intent.putExtra("order",gsonOrder);
                context.startActivity(intent);
            }
        });
        holder.item_order_good3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, OrderActivity.class);
                intent.putExtra("order",gsonOrder);
                context.startActivity(intent);
            }
        });
        holder.item_order_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, OrderActivity.class);
                intent.putExtra("order",gsonOrder);
                context.startActivity(intent);
            }
        });
        holder.item_order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                id=gsonOrder.getId();
                handler.postDelayed(runnable, 2000);//每两秒执行一次runnable.
                pos=position;
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void getGoodName(final order_adapter.ViewHolder holder, int goodId, String shopphone, final int i, final int select){
        Retrofit retrofit = RetrofitUtil.getRetrofit(localhost + good);
        getGoodsById getGoodsById=retrofit.create(getGoodsById.class);
        getGoodsById.getGoods(goodId,shopphone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GsonGood>() {
                    private Disposable disposable;
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(GsonGood gsonGood) {
                        String goodName=gsonGood.getName();
                        if(i==0){
                            holder.item_order_good1.setLeftString(goodName);
                            holder.item_order_good1.setRightString("X"+select);
                            holder.item_order_good1.setVisibility(View.VISIBLE);
                        }
                        if(i==1){
                            holder.item_order_good2.setLeftString(goodName);
                            holder.item_order_good2.setRightString("X"+select);
                            holder.item_order_good2.setVisibility(View.VISIBLE);
                        }
                        if(i==2){
                            holder.item_order_good3.setLeftString(goodName);
                            holder.item_order_good3.setRightString("X"+select);
                            holder.item_order_good3.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        disposable.dispose();
                        Log.e("getGood",e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //要做的事情
            sureOrder();
        }
    };
    public void sureOrder(){
        Retrofit retrofit=RetrofitUtil.getRetrofit(localhost+orders);
        sureOrder sureOrder=retrofit.create(sureOrder.class);
        sureOrder.sureOrder(id,UserUtil.gsonUsers.getUserphone())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GsonOrder>() {
                    private Disposable disposable;
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(GsonOrder gsonOrder) {
                        orderList.get(pos).setOrderstate("订单已完成");
                        notifyItemChanged(pos);
                        UserUtil.setList(UserUtil.gsonUsers.getUserphone());
                        dialog.dismiss();
                        handler.removeCallbacks(runnable);
                    }

                    @Override
                    public void onError(Throwable e) {
                        disposable.dispose();
                        handler.removeCallbacks(runnable);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
