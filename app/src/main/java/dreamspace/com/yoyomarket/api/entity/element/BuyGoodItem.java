package dreamspace.com.yoyomarket.api.entity.element;

/**
 * Created by Lx on 2016/1/30.
 */
public class BuyGoodItem {
    private String goods_id;
    private int quantity;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
