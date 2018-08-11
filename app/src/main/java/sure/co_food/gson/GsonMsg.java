package sure.co_food.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dell88 on 2018/2/11 0011.
 */

public class GsonMsg {
    @SerializedName("meg")
    @Expose
    private String meg;

    public String getMeg() {
        return meg;
    }

    public void setMeg(String meg) {
        this.meg = meg;
    }
}
