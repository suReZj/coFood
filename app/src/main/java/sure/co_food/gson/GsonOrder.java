package sure.co_food.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dell88 on 2018/2/11 0011.
 */

public class GsonOrder implements Serializable{
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("userphone")
    @Expose
    private String userphone;

    @SerializedName("shopphone")
    @Expose
    private String shopphone;

    @SerializedName("payway")
    @Expose
    private String payway;

    @SerializedName("goodsum")
    @Expose
    private Integer goodsum;

    @SerializedName("totalprice")
    @Expose
    private Double totalprice;

    @SerializedName("orderstate")
    @Expose
    private String orderstate;

    @SerializedName("shopname")
    @Expose
    private String shopname;

    @SerializedName("ordertime")
    @Expose
    private String ordertime;

    @SerializedName("goodlist")
    @Expose
    private String goodlist;

    @SerializedName("eachgoodsum")
    @Expose
    private String eachgoodsum;

    @SerializedName("usernotice")
    @Expose
    private String usernotice;

    @SerializedName("userlocation")
    @Expose
    private String userlocation;

    @SerializedName("shopimagepath")
    @Expose
    private String shopimagepath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }

    public Integer getGoodsum() {
        return goodsum;
    }

    public void setGoodsum(Integer goodsum) {
        this.goodsum = goodsum;
    }

    public Double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Double totalprice) {
        this.totalprice = totalprice;
    }

    public String getOrderstate() {
        return orderstate;
    }

    public void setOrderstate(String orderstate) {
        this.orderstate = orderstate;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public String getGoodlist() {
        return goodlist;
    }

    public void setGoodlist(String goodlist) {
        this.goodlist = goodlist;
    }

    public String getEachgoodsum() {
        return eachgoodsum;
    }

    public void setEachgoodsum(String eachgoodsum) {
        this.eachgoodsum = eachgoodsum;
    }

    public String getUsernotice() {
        return usernotice;
    }

    public void setUsernotice(String usernotice) {
        this.usernotice = usernotice;
    }

    public String getUserlocation() {
        return userlocation;
    }

    public void setUserlocation(String userlocation) {
        this.userlocation = userlocation;
    }

    public String getShopimagepath() {
        return shopimagepath;
    }

    public void setShopimagepath(String shopimagepath) {
        this.shopimagepath = shopimagepath;
    }
}
