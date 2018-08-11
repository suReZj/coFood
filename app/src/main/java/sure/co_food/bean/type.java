package sure.co_food.bean;

import java.io.Serializable;

/**
 * Created by dell88 on 2018/1/14 0014.
 */

public class type implements Serializable {
    String id;

    public type(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
