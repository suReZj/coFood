package sure.co_food.bean;

import java.util.List;

/**
 * Created by dell88 on 2018/2/14 0014.
 */

public class searchList {
    private List<search> list;

    public searchList() {
    }

    public searchList(List<search> list) {
        this.list = list;
    }

    public List<search> getList() {
        return list;
    }

    public void setList(List<search> list) {
        this.list = list;
    }
}
