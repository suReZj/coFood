package sure.co_food.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dell88 on 2017/12/24 0024.
 */

public class order implements Serializable {
    private int id;
    private int userPhone;
    private int shopPhone;
    private Date orderDate;

    public order() {
    }


    public order(int id, int userPhone, int shopPhone, Date orderDate) {
        this.id = id;
        this.userPhone = userPhone;
        this.shopPhone = shopPhone;
        this.orderDate = orderDate;
    }

    public int getUserPhone() {
        return userPhone;
    }

    public int getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(int shopPhone) {
        this.shopPhone = shopPhone;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserPhone(int userPhone) {
        this.userPhone = userPhone;
    }



    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
