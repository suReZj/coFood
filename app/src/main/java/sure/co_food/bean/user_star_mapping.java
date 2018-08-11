package sure.co_food.bean;

import java.io.Serializable;

/**
 * Created by dell88 on 2018/2/7 0007.
 */

public class user_star_mapping implements Serializable {
    private String userPhone;
    private String shopPhone;

    public user_star_mapping() {
    }

    public user_star_mapping(String userPhone, String shopPhone) {
        this.userPhone = userPhone;
        this.shopPhone = shopPhone;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }
}
