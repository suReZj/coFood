package sure.co_food.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dell88 on 2018/2/12 0012.
 */

public class GsonStars {
    @SerializedName("list")
    @Expose
    private List<GsonStar> list;

    public List<GsonStar> getList() {
        return list;
    }

    public void setList(List<GsonStar> list) {
        this.list = list;
    }
}
