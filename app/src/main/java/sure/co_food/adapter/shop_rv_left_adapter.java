package sure.co_food.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sure.co_food.R;
import sure.co_food.bean.type;

/**
 * Created by dell88 on 2018/1/14 0014.
 */

public class shop_rv_left_adapter extends RecyclerView.Adapter<shop_rv_left_adapter.ViewHolder> {
    private Context context;
    private List<String> typeList = new ArrayList<>();
    private OnItemClickLitener mOnItemClickLitener;
    private int clickPosition = 0;

    public void setClickPosition(String id) {
        for(int i=0;i<typeList.size();i++){
            if(typeList.get(i).equals(id)){
                clickPosition=i;
                notifyDataSetChanged();
                break;
            }
        }
    }

    public int getClickPosition() {
        return clickPosition;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.shop_rv_left_layout);
            textView = itemView.findViewById(R.id.item_shop_rv_left_text);
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    public shop_rv_left_adapter(List<String> typeList) {
        this.typeList = typeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_shop_rv_left, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String type = typeList.get(position);
        holder.textView.setText(type);
        if (position == clickPosition) {
            holder.layout.setBackgroundColor(Color.parseColor("#9EABF4"));
            holder.textView.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.layout.setBackgroundColor(Color.parseColor("#00FFFFFF"));//设置为透明的，因为白色会覆盖分割线
            holder.textView.setTextColor(Color.parseColor("#1e1d1d"));
        }

        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    notifyDataSetChanged();
                    clickPosition = position;
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }


}
