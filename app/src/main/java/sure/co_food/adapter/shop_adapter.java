package sure.co_food.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;

import java.util.List;

import sure.co_food.Activity.ShopActivity;
import sure.co_food.R;
import sure.co_food.bean.shop;
import sure.co_food.gson.GsonShop;

import static sure.co_food.Contants.distribution;
import static sure.co_food.Contants.imageLocalhost;
import static sure.co_food.Contants.price;
import static sure.co_food.Contants.stringDistribution;

/**
 * Created by dell88 on 2017/12/27 0027.
 */

public class shop_adapter extends RecyclerView.Adapter<shop_adapter.ViewHolder> {
    private Context context;
    private List<GsonShop> shopList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private SuperTextView superTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            superTextView=itemView.findViewById(R.id.shop_superText);
        }
    }

    public shop_adapter(List<GsonShop> shopList) {
        this.shopList = shopList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(context==null){
            context=parent.getContext();
        }
        View view= LayoutInflater.from(context).inflate(R.layout.item_shop,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final GsonShop shop=shopList.get(position);
        holder.superTextView.setLeftTopString(shop.getShopname());
        holder.superTextView.setLeftBottomString(price+(int)shop.getShopminprice()+stringDistribution+"|"+distribution+(int)shop.getShopdistribution());
        holder.superTextView.setRightTopString("月售"+shop.getShopsalessum()+"单");
        Glide.with(context).load(imageLocalhost+shop.getShopimagepath()).into(holder.superTextView.getLeftIconIV());
        holder.superTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ShopActivity.class);
                intent.putExtra("shop",shop);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }
}
