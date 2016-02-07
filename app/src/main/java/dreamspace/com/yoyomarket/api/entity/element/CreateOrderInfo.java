package dreamspace.com.yoyomarket.api.entity.element;

import java.util.ArrayList;

/**
 * Created by Lx on 2016/1/30.
 */
public class CreateOrderInfo {
    private String address_id;
    private double price;
    private String remark;
    private String sup_id;
    private String order_time;
    private String deliver_time;
    private ArrayList<BuyGoodItem> goods;

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSup_id() {
        return sup_id;
    }

    public void setSup_id(String sup_id) {
        this.sup_id = sup_id;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getDeliver_time() {
        return deliver_time;
    }

    public void setDeliver_time(String deliver_time) {
        this.deliver_time = deliver_time;
    }

    public ArrayList<BuyGoodItem> getGoods() {
        return goods;
    }

    public void setGoods(ArrayList<BuyGoodItem> goods) {
        this.goods = goods;
    }
}
