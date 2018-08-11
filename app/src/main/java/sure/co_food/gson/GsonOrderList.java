package sure.co_food.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dell88 on 2018/2/11 0011.
 */

public class GsonOrderList {
    @SerializedName("list")
    @Expose
    private List<GsonOrder> list;

    public List<GsonOrder> getList() {
        return list;
    }

    public void setList(List<GsonOrder> list) {
        this.list = list;
    }
}
