package dreamspace.com.yoyomarket.api.entity.element;

/**
 * Created by Lx on 2016/2/28.
 */
public class OrderItem {
    private String order_id;
    private int status;
    private String image;
    private String deliver_time;
    private String order_time;
    private String sup_phone_num;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDeliver_time() {
        return deliver_time;
    }

    public void setDeliver_time(String deliver_time) {
        this.deliver_time = deliver_time;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getSup_phone_num() {
        return sup_phone_num;
    }

    public void setSup_phone_num(String sup_phone_num) {
        this.sup_phone_num = sup_phone_num;
    }
}
