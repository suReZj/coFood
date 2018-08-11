package sure.co_food.bean;

import java.io.Serializable;

/**
 * Created by dell88 on 2018/2/7 0007.
 */

public class order_goods_mapping implements Serializable {
    private int orderId;
    private int goodsId;

    public order_goods_mapping() {
    }

    public order_goods_mapping(int orderId, int goodsId) {
        this.orderId = orderId;
        this.goodsId = goodsId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }
}
