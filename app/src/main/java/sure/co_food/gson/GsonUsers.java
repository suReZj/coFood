package sure.co_food.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by dell88 on 2018/2/7 0007.
 */

public class GsonUsers  {
    @SerializedName("userphone")
    @Expose
    private String userphone;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("usercity")
    @Expose
    private String usercity;

    @SerializedName("userpassword")
    @Expose
    private String userpassword;

    @SerializedName("isshop")
    @Expose
    private boolean isshop;

    @SerializedName("userlocation")
    @Expose
    private String userlocation;

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsercity() {
        return usercity;
    }

    public void setUsercity(String usercity) {
        this.usercity = usercity;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public boolean isIsshop() {
        return isshop;
    }

    public void setIsshop(boolean isshop) {
        this.isshop = isshop;
    }

    public String getUserlocation() {
        return userlocation;
    }

    public void setUserlocation(String userlocation) {
        this.userlocation = userlocation;
    }

}
