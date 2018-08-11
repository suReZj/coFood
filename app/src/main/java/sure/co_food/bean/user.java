package sure.co_food.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell88 on 2017/12/24 0024.
 */

public class user implements Serializable {
    private String userName;
    private String userPhone;//ID
    private String userCity;
    private String userPassword;
    private boolean isShop;//是否为商家
    private String userLocation;

    public user() {
    }

    public user(String userName, String userPhone, String userCity, String userPassword, boolean isShop, String userLocation) {
        this.userName = userName;
        this.userPhone = userPhone;
        this.userCity = userCity;
        this.userPassword = userPassword;
        this.isShop = isShop;
        this.userLocation = userLocation;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }


    public boolean isShop() {
        return isShop;
    }

    public void setShop(boolean shop) {
        isShop = shop;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

}
