package sure.co_food.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dell88 on 2018/2/8 0008.
 */

public class GsonShop implements Serializable{
    @SerializedName("shopphone")
    @Expose
    private String shopphone;

    @SerializedName("userphone")
    @Expose
    private String userphone;

    @SerializedName("shoplocation")
    @Expose
    private String shoplocation;

    @SerializedName("shopimagepath")
    @Expose
    private String shopimagepath;

    @SerializedName("shopname")
    @Expose
    private String shopname;

    @SerializedName("shopnotice")
    @Expose
    private String shopnotice;

    @SerializedName("shopdistribution")
    @Expose
    private double shopdistribution;

    @SerializedName("shopminprice")
    @Expose
    private double shopminprice;

    @SerializedName("shopcity")
    @Expose
    private String shopcity;

    @SerializedName("shopsalessum")
    @Expose
    private int shopsalessum;

    public String getShopphone() {
        return shopphone;
    }

    public void setShopphone(String shopphone) {
        this.shopphone = shopphone;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getShoplocation() {
        return shoplocation;
    }

    public void setShoplocation(String shoplocation) {
        this.shoplocation = shoplocation;
    }

    public String getShopimagepath() {
        return shopimagepath;
    }

    public void setShopimagepath(String shopimagepath) {
        this.shopimagepath = shopimagepath;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getShopnotice() {
        return shopnotice;
    }

    public void setShopnotice(String shopnotice) {
        this.shopnotice = shopnotice;
    }

    public double getShopdistribution() {
        return shopdistribution;
    }

    public void setShopdistribution(double shopdistribution) {
        this.shopdistribution = shopdistribution;
    }

    public double getShopminprice() {
        return shopminprice;
    }

    public void setShopminprice(double shopminprice) {
        this.shopminprice = shopminprice;
    }

    public String getShopcity() {
        return shopcity;
    }

    public void setShopcity(String shopcity) {
        this.shopcity = shopcity;
    }

    public int getShopsalessum() {
        return shopsalessum;
    }

    public void setShopsalessum(int shopsalessum) {
        this.shopsalessum = shopsalessum;
    }
}
