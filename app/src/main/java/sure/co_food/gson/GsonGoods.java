package sure.co_food.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dell88 on 2018/2/9 0009.
 */

public class GsonGoods {
    @SerializedName("list")
    @Expose
    private List<GsonGood> list;

    public List<GsonGood> getList() {
        return list;
    }

    public void setList(List<GsonGood> list) {
        this.list = list;
    }
}
