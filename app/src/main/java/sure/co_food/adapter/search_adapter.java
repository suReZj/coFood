package sure.co_food.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import sure.co_food.Activity.ShopActivity;
import sure.co_food.R;
import sure.co_food.bean.search;
import sure.co_food.bean.shop;
import sure.co_food.gson.GsonShop;
import sure.co_food.retrofit.getShopById;
import sure.co_food.util.RetrofitUtil;

import static sure.co_food.Contants.Shop;
import static sure.co_food.Contants.distribution;
import static sure.co_food.Contants.imageLocalhost;
import static sure.co_food.Contants.localhost;
import static sure.co_food.Contants.price;
import static sure.co_food.Contants.stringDistribution;

/**
 * Created by dell88 on 2018/2/5 0005.
 */

public class search_adapter extends RecyclerView.Adapter<search_adapter.ViewHolder> {
    private Context context;
    private List<search> shopList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        private SuperTextView search_item_superText;
        private SuperTextView search_item_good1;
        private SuperTextView search_item_good2;
        private TextView search_item_textView;
        private CardView search_item;
        public ViewHolder(View itemView) {
            super(itemView);
            search_item_superText=itemView.findViewById(R.id.search_item_superText);
            search_item_good1=itemView.findViewById(R.id.search_item_good1);
            search_item_good2=itemView.findViewById(R.id.search_item_good2);
            search_item_textView=itemView.findViewById(R.id.search_item_textView);
            search_item=itemView.findViewById(R.id.search_item);
        }
    }

    public search_adapter(List<search> shopList) {
        this.shopList = shopList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(context==null){
            context=parent.getContext();
        }
        View view= LayoutInflater.from(context).inflate(R.layout.item_search,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        search search=shopList.get(position);
        for(int i=0;i<search.getList().size();i++){
            if(i==0){
                holder.search_item_good1.setLeftTopString(search.getList().get(i).getName());
                holder.search_item_good1.setLeftBottomString("月售"+search.getList().get(i).getSalessum()+"份");
                holder.search_item_good1.setRightTopString("¥"+search.getList().get(i).getPrice());
                Glide.with(context).load(imageLocalhost+search.getList().get(i).getImagepath()).into(holder.search_item_good1.getLeftIconIV());
                holder.search_item_good1.setVisibility(View.VISIBLE);
            }
            if(i==1){
                holder.search_item_good2.setLeftTopString(search.getList().get(i).getName());
                holder.search_item_good2.setLeftBottomString("月售"+search.getList().get(i).getSalessum()+"份");
                holder.search_item_good2.setRightTopString("¥"+search.getList().get(i).getPrice());
                Glide.with(context).load(imageLocalhost+search.getList().get(i).getImagepath()).into(holder.search_item_good2.getLeftIconIV());
                holder.search_item_good2.setVisibility(View.VISIBLE);
            }
            if(i==2){
                holder.search_item_textView.setVisibility(View.VISIBLE);
                i=search.getList().size();
            }
        }
        getShop(holder,shopList.get(position).getShopphone());
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public void getShop(final ViewHolder holder, String shopphone){
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
                    public void onNext(final GsonShop shop) {
                        holder.search_item_superText.setLeftTopString(shop.getShopname());
                        holder.search_item_superText.setLeftBottomString(price+(int)shop.getShopminprice()+stringDistribution+"|"+distribution+(int)shop.getShopdistribution());
                        holder.search_item_superText.setRightTopString("月售"+shop.getShopsalessum()+"单");
                        Glide.with(context).load(imageLocalhost+shop.getShopimagepath()).into(holder.search_item_superText.getLeftIconIV());
                        holder.search_item_superText.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(context, ShopActivity.class);
                                intent.putExtra("shop",shop);
                                context.startActivity(intent);
                            }
                        });
                        holder.search_item_good1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(context, ShopActivity.class);
                                intent.putExtra("shop",shop);
                                context.startActivity(intent);
                            }
                        });
                        holder.search_item_good2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(context, ShopActivity.class);
                                intent.putExtra("shop",shop);
                                context.startActivity(intent);
                            }
                        });
                        holder.search_item_textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(context, ShopActivity.class);
                                intent.putExtra("shop",shop);
                                context.startActivity(intent);
                            }
                        });
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
