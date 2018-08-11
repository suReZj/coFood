package sure.co_food.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dell88 on 2018/2/8 0008.
 */

public class GsonShops {
    @SerializedName("list")
    @Expose
    private List<GsonShop> list=null;

    public List<GsonShop> getList() {
        return list;
    }

    public void setList(List<GsonShop> list) {
        this.list = list;
    }
}
