package sure.co_food.event;

import at.markushi.ui.CircleButton;
import sure.co_food.bean.goods;

/**
 * Created by dell88 on 2018/1/20 0020.
 */

public class addGoodsEvent {
    private CircleButton btn=null;
    private goods good;
    private Boolean flag;
    //true为添加商品，false为删减商品

    public addGoodsEvent(goods good, Boolean flag) {
        this.good = good;
        this.flag = flag;
    }

    public addGoodsEvent(CircleButton btn, goods good, Boolean flag) {
        this.btn = btn;
        this.good = good;
        this.flag = flag;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public CircleButton getBtn() {
        return btn;
    }

    public void setBtn(CircleButton btn) {
        this.btn = btn;
    }

    public goods getGood() {
        return good;
    }

    public void setGood(goods good) {
        this.good = good;
    }
}
