package sure.co_food.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import sure.co_food.R;
import sure.co_food.gson.GsonGood;
import sure.co_food.retrofit.getGoodsById;
import sure.co_food.util.RetrofitUtil;

import static sure.co_food.Contants.cheng;
import static sure.co_food.Contants.good;
import static sure.co_food.Contants.localhost;
import static sure.co_food.Contants.price;

/**
 * Created by dell88 on 2018/2/12 0012.
 */

public class order_info_adapter extends RecyclerView.Adapter<order_info_adapter.ViewHolder>{
    private Context context;
    private int array[];
    private int selectArray[];
    private String shopphone;
    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView item_payment_goods_name;
        private TextView item_payment_goods_price;
        private TextView item_payment_goods_sum;
        public ViewHolder(View itemView) {
            super(itemView);
            item_payment_goods_name=itemView.findViewById(R.id.item_payment_goods_name);
            item_payment_goods_price=itemView.findViewById(R.id.item_payment_goods_price);
            item_payment_goods_sum=itemView.findViewById(R.id.item_payment_goods_sum);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_payment, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        getGood(holder,array[position],Integer.valueOf(selectArray[position]));
        holder.item_payment_goods_sum.setText(cheng+selectArray[position]);
    }

    @Override
    public int getItemCount() {
        return array.length;
    }

    public order_info_adapter(int[] array, int[] selectArray, String shopphone) {
        this.array = array;
        this.selectArray = selectArray;
        this.shopphone = shopphone;
    }

    public void getGood(final ViewHolder holder, int id , final int selectSum){
        Retrofit retrofit = RetrofitUtil.getRetrofit(localhost + good);
        getGoodsById getGoodsById=retrofit.create(getGoodsById.class);
        getGoodsById.getGoods(id,shopphone)
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
                        holder.item_payment_goods_name.setText(goodName);
                        holder.item_payment_goods_price.setText(price+selectSum*gsonGood.getPrice());
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
}
