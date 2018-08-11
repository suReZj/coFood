package sure.co_food.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


import at.markushi.ui.CircleButton;
import sure.co_food.Activity.ShopActivity;
import sure.co_food.R;
import sure.co_food.bean.goods;
import sure.co_food.event.addGoodsEvent;

import static sure.co_food.Contants.imageLocalhost;
import static sure.co_food.Contants.moothSales;
import static sure.co_food.Contants.price;
import static sure.co_food.Contants.sum;
import static sure.co_food.util.ShowDialog.showGoodsDialog;

/**
 * Created by dell88 on 2018/1/14 0014.
 */

public class shop_rv_right_adapter extends RecyclerView.Adapter<shop_rv_right_adapter.ViewHolder> {
    // RecyclerView 的第一个item，肯定是展示StickyLayout的.
    public static final int FIRST_STICKY_VIEW = 1;
    // RecyclerView 除了第一个item以外，要展示StickyLayout的.
    public static final int HAS_STICKY_VIEW = 2;
    // RecyclerView 的不展示StickyLayout的item.
    public static final int NONE_STICKY_VIEW = 3;

    private Context context;
    private List<goods> goodsList = new ArrayList<>();
    private OnItemClickLitener mOnItemClickLitener;
    private int clickPosition = 0;


    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvStickyHeader;
        private TextView goodName;
        private ImageView goodImage;
        private TextView goodPrice;
        private TextView moothSale;
        private CircleButton leftBtn;
        private CircleButton rightBtn;
        private TextView selectSum;
        private LinearLayout contain;

        public ViewHolder(View itemView) {
            super(itemView);
            contain=itemView.findViewById(R.id.item_shop_rv_right_contain);
            tvStickyHeader = itemView.findViewById(R.id.tv_sticky_header_view);
            goodName = itemView.findViewById(R.id.item_shop_rv_right_name);
            goodImage = itemView.findViewById(R.id.item_shop_rv_right_image);
            goodPrice = itemView.findViewById(R.id.item_shop_rv_right_price);
            moothSale = itemView.findViewById(R.id.item_shop_rv_right_month);
            leftBtn = itemView.findViewById(R.id.item_shop_rv_right_reduce);
            rightBtn = itemView.findViewById(R.id.item_shop_rv_right_add);
            selectSum = itemView.findViewById(R.id.item_shop_rv_right_selectSum);
        }
    }
    public int getClickPosition() {
        return clickPosition;
    }
    public shop_rv_right_adapter(List<goods> goodsList) {
        this.goodsList = goodsList;
    }


    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_shop_rv_right, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final goods goods = goodsList.get(position);
        if (position == 0) {
            holder.tvStickyHeader.setVisibility(View.VISIBLE);
            holder.tvStickyHeader.setText(goods.getType());

            // 第一个item的吸顶信息肯定是展示的，并且标记tag为FIRST_STICKY_VIEW
            holder.itemView.setTag(FIRST_STICKY_VIEW);

        } else {
            // 之后的item都会和前一个item要展示的吸顶信息进行比较，不相同就展示，并且标记tag为HAS_STICKY_VIEW
            if (!TextUtils.equals(goods.getType(), goodsList.get(position - 1).getType())) {
                holder.tvStickyHeader.setVisibility(View.VISIBLE);
                holder.tvStickyHeader.setText(goods.getType());
                holder.itemView.setTag(HAS_STICKY_VIEW);

            } else {
                // 相同就不展示，并且标记tag为NONE_STICKY_VIEW
                holder.tvStickyHeader.setVisibility(View.GONE);
                holder.itemView.setTag(NONE_STICKY_VIEW);

            }
        }
// ContentDescription 用来记录并获取要吸顶展示的信息
        holder.itemView.setContentDescription(goods.getType());


        holder.goodName.setText(goods.getName());
        holder.goodPrice.setText(price + goods.getPrice());
        holder.moothSale.setText(moothSales + goods.getSalessum() + sum);
        holder.selectSum.setText(String.valueOf(goods.getSelectSum()));
        if (goods.getSelectSum() == 0) {
            holder.leftBtn.setVisibility(View.GONE);
            holder.selectSum.setVisibility(View.GONE);
        }else {
            holder.leftBtn.setVisibility(View.VISIBLE);
            holder.selectSum.setVisibility(View.VISIBLE);
        }
        holder.rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sum=goods.getSelectSum();
                goods.setSelectSum(sum+1);
                holder.selectSum.setText(String.valueOf(goods.getSelectSum()));
                holder.selectSum.setVisibility(View.VISIBLE);
                holder.leftBtn.setVisibility(View.VISIBLE);
                notifyDataSetChanged();
                holder.selectSum.setVisibility(View.VISIBLE);
                EventBus.getDefault().post(new addGoodsEvent(holder.rightBtn,goodsList.get(position),true));
            }
        });
        holder.leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sum=goods.getSelectSum();
                goods.setSelectSum(sum-1);
                holder.selectSum.setText(String.valueOf(goods.getSelectSum()));
                if(goods.getSelectSum()==0){
                    holder.leftBtn.setVisibility(View.GONE);
                    holder.selectSum.setVisibility(View.GONE);
                }
                notifyDataSetChanged();
                EventBus.getDefault().post(new addGoodsEvent(holder.rightBtn,goodsList.get(position),false));
            }
        });


        holder.contain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("111111111","11111111");
                goods goods=goodsList.get(position);
                showGoodsDialog(context,goods);
            }
        });
        Glide.with(context).load(imageLocalhost+goods.getImagepath()).into(holder.goodImage);
    }


    @Override
    public int getItemCount() {
        return goodsList.size();
    }
}
