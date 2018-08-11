package sure.co_food.bean;

import java.io.Serializable;

/**
 * Created by dell88 on 2018/2/7 0007.
 */

public class shop_goods_mapping implements Serializable {
    private String shopPhone;
    private int goodId;

    public shop_goods_mapping() {
    }

    public shop_goods_mapping(String shopPhone, int goodId) {
        this.shopPhone = shopPhone;
        this.goodId = goodId;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }
}
