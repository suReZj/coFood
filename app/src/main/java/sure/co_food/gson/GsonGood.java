package sure.co_food.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dell88 on 2018/2/9 0009.
 */

public class GsonGood implements Serializable {
    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("price")
    @Expose
    private Double price;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("imagepath")
    @Expose
    private String imagepath;

    @SerializedName("salessum")
    @Expose
    private Integer salessum;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("shopphone")
    @Expose
    private String shopphone;

    @SerializedName("shopname")
    @Expose
    private String shopName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getShopphone() {
        return shopphone;
    }

    public void setShopphone(String shopphone) {
        this.shopphone = shopphone;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
