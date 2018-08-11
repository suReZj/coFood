package sure.co_food.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import sure.co_food.R;
import sure.co_food.bean.goods;

import static sure.co_food.Contants.imageLocalhost;
import static sure.co_food.Contants.moothSales;
import static sure.co_food.Contants.price;
import static sure.co_food.Contants.sum;

/**
 * Created by dell88 on 2018/1/23 0023.
 */

public class ShowDialog {
    public static void showImageDialog(Context context,String shopphone){
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_show_qr, null);
        Dialog bottomDialog = new Dialog(context, R.style.BottomDialog);
        final ImageView imageView = (ImageView) contentView.findViewById(R.id.QR_image);
        Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap(shopphone, 480, 480);
        imageView.setImageBitmap(mBitmap);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = context.getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px(context, 16f);
        params.bottomMargin = DensityUtil.dp2px(context, 8f);
        contentView.setLayoutParams(params);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.CENTER);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }
    public static void showGoodsDialog(Context context,goods goods){
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_show_goods, null);
        Dialog bottomDialog = new Dialog(context, R.style.BottomDialog);


        final ImageView imageView = contentView.findViewById(R.id.dialog_goods_image);
        final TextView textView_name=contentView.findViewById(R.id.dialog_goods_name);
        final TextView textView_price=contentView.findViewById(R.id.dialog_goods_price);
        final TextView textView_sales=contentView.findViewById(R.id.dialog_goods_sales);


        textView_name.setText(goods.getName());
        textView_price.setText(price+goods.getPrice());
        textView_sales.setText(moothSales + goods.getSalessum() + sum);

        Glide.with(context).load(imageLocalhost+goods.getImagepath()).into(imageView);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = context.getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px(context, 16f);
        params.bottomMargin = DensityUtil.dp2px(context, 8f);
        contentView.setLayoutParams(params);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.CENTER);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }
}
