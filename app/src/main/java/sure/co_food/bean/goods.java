package sure.co_food.bean;

import java.io.Serializable;

import sure.co_food.gson.GsonGood;

/**
 * Created by dell88 on 2018/1/14 0014.
 */

public class goods implements Serializable{
    private double price;
    private String name;
    private String type;
    private String imagepath;
    private Integer salessum;
    private Integer id;
    private int selectSum;
    private String shopphone;

    public goods() {
    }

public goods(GsonGood good){
        this.price=good.getPrice();
        this.name=good.getName();
        this.type=good.getType();
        this.imagepath=good.getImagepath();
        this.salessum=good.getSalessum();
        this.id=good.getId();
        this.shopphone=good.getShopphone();
        this.selectSum=0;
}

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public Integer getSalessum() {
        return salessum;
    }

    public void setSalessum(Integer salessum) {
        this.salessum = salessum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getSelectSum() {
        return selectSum;
    }

    public void setSelectSum(int selectSum) {
        this.selectSum = selectSum;
    }

    public String getShopphone() {
        return shopphone;
    }

    public void setShopphone(String shopphone) {
        this.shopphone = shopphone;
    }
}
