package dreamspace.com.yoyomarket.api.entity.element;

import java.util.ArrayList;

/**
 * Created by Lx on 2016/1/30.
 */
public class OrderInfo {
    private String name;
    private String address;
    private String phone_num;
    private int price;
    private String remark;
    private String sup_id;
    private String order_time;
    private String deliver_time;
    private int status;
    private String buyer;
    private String sup_phone_num;
    private ArrayList<OrderGoodsItem> goods;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
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

    public ArrayList<OrderGoodsItem> getGoods() {
        return goods;
    }

    public void setGoods(ArrayList<OrderGoodsItem> goods) {
        this.goods = goods;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getSup_phone_num() {
        return sup_phone_num;
    }

    public void setSup_phone_num(String sup_phone_num) {
        this.sup_phone_num = sup_phone_num;
    }
}
