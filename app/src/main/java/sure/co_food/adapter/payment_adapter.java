package sure.co_food.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sure.co_food.R;
import sure.co_food.bean.goods;

import static sure.co_food.Contants.cheng;
import static sure.co_food.Contants.price;

/**
 * Created by dell88 on 2018/1/24 0024.
 */

public class payment_adapter extends RecyclerView.Adapter<payment_adapter.ViewHolder> {
    private List<goods> list = new ArrayList<>();
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
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

    public payment_adapter(List<goods> list) {
        this.list = list;
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
        goods goods=list.get(position);
        holder.item_payment_goods_name.setText(goods.getName());
        holder.item_payment_goods_price.setText(price+goods.getPrice()*goods.getSelectSum());
        holder.item_payment_goods_sum.setText(cheng+goods.getSelectSum());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
