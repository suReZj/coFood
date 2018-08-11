package sure.co_food.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell88 on 2017/12/24 0024.
 */

public class shop implements Serializable{
    private String userPhone;
    private String shopLocation;
    private String shopImagePath;
    private String shopName;
    private String shopNotice;
    private String shopPhone;//商家ID
    private double shopDistribution;//配送费
    private double shopMinPrice;//起送费
    private String shopCity;
    private List<goods> shopGoodsList=new ArrayList<>();
    private List<goods> selectedGoods=new ArrayList<>();
    public shop() {
    }


    public shop(String userPhone, String shopLocation, String shopImagePath, String shopName, String shopNotice, String shopPhone, double shopDistribution, double shopMinPrice, String shopCity, List<goods> shopGoodsList, List<goods> selectedGoods) {
        this.userPhone = userPhone;
        this.shopLocation = shopLocation;
        this.shopImagePath = shopImagePath;
        this.shopName = shopName;
        this.shopNotice = shopNotice;
        this.shopPhone = shopPhone;
        this.shopDistribution = shopDistribution;
        this.shopMinPrice = shopMinPrice;
        this.shopCity = shopCity;
        this.shopGoodsList = shopGoodsList;
        this.selectedGoods = selectedGoods;
    }

    public String getShopCity() {
        return shopCity;
    }

    public void setShopCity(String shopCity) {
        this.shopCity = shopCity;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public List<goods> getSelectedGoods() {
        return selectedGoods;
    }

    public void setSelectedGoods(List<goods> selectedGoods) {
        this.selectedGoods = selectedGoods;
    }

    public String getShopLocation() {
        return shopLocation;
    }

    public void setShopLocation(String shopLocation) {
        this.shopLocation = shopLocation;
    }

    public String getShopImagePath() {
        return shopImagePath;
    }

    public void setShopImagePath(String shopImagePath) {
        this.shopImagePath = shopImagePath;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopNmae) {
        this.shopName = shopNmae;
    }

    public String getShopNotice() {
        return shopNotice;
    }

    public void setShopNotice(String shopNotice) {
        this.shopNotice = shopNotice;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public double getShopDistribution() {
        return shopDistribution;
    }

    public void setShopDistribution(double shopDistribution) {
        this.shopDistribution = shopDistribution;
    }

    public double getShopMinPrice() {
        return shopMinPrice;
    }

    public void setShopMinPrice(double shopMinPrice) {
        this.shopMinPrice = shopMinPrice;
    }

    public List<goods> getShopGoodsList() {
        return shopGoodsList;
    }

    public void setShopGoodsList(List<goods> shopGoodsList) {
        this.shopGoodsList = shopGoodsList;
    }
}
