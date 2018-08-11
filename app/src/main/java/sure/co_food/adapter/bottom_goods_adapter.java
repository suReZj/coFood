package sure.co_food.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import at.markushi.ui.CircleButton;
import sure.co_food.R;
import sure.co_food.bean.goods;
import sure.co_food.event.addGoodsEvent;

import static sure.co_food.Contants.price;

/**
 * Created by dell88 on 2018/1/20 0020.
 */

public class bottom_goods_adapter extends RecyclerView.Adapter<bottom_goods_adapter.ViewHolder> {
    private List<goods> goodsList = new ArrayList<>();
    private Context context;

    public bottom_goods_adapter(List<goods> goodsList) {
        this.goodsList = goodsList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView goodsName;
        private TextView selectedSum;
        private TextView goodsPrice;
        private CircleButton item_bottom_goods_reduce;
        private CircleButton item_bottom_goods_add;

        public ViewHolder(View itemView) {
            super(itemView);
            item_bottom_goods_add = itemView.findViewById(R.id.item_bottom_goods_add);
            item_bottom_goods_reduce = itemView.findViewById(R.id.item_bottom_goods_reduce);
            goodsName = itemView.findViewById(R.id.item_bottom_goods_name);
            selectedSum = itemView.findViewById(R.id.item_bottom_goods_sum);
            goodsPrice = itemView.findViewById(R.id.item_bottom_goods_price);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_bottom_goods_rv, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final goods goods = goodsList.get(position);
        holder.setIsRecyclable(false);
        holder.goodsName.setText(goods.getName());
        holder.goodsPrice.setText(price + String.valueOf(goods.getPrice()*goods.getSelectSum()));
        holder.selectedSum.setText(String.valueOf(goods.getSelectSum()));
        holder.item_bottom_goods_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sum=goods.getSelectSum();
                goods.setSelectSum(sum+1);
                notifyDataSetChanged();
                EventBus.getDefault().post(new addGoodsEvent(goods, true));
            }
        });
        holder.item_bottom_goods_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sum=goods.getSelectSum();
                goods.setSelectSum(sum-1);
                notifyDataSetChanged();
                EventBus.getDefault().post(new addGoodsEvent(goods, false));
            }
        });
    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }
}
