package sure.co_food.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dell88 on 2018/2/12 0012.
 */

public class GsonStar {
    @SerializedName("userphone")
    @Expose
    private String userphone;

    @SerializedName("shopphone")
    @Expose
    private String shopphone;

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getShopphone() {
        return shopphone;
    }

    public void setShopphone(String shopphone) {
        this.shopphone = shopphone;
    }
}
