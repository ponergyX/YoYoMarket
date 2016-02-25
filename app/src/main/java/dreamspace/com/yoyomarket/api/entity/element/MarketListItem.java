package dreamspace.com.yoyomarket.api.entity.element;

/**
 * Created by Lx on 2016/1/28.
 */
public class MarketListItem {
    private String sup_id;
    private String name;
    private String image;
    private int sales_number;
    private int score;
    private int deliver_fee;
    private int send_price;

    public String getSup_id() {
        return sup_id;
    }

    public void setSup_id(String sup_id) {
        this.sup_id = sup_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getSales_number() {
        return sales_number;
    }

    public void setSales_number(int sales_number) {
        this.sales_number = sales_number;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getDeliver_fee() {
        return deliver_fee;
    }

    public void setDeliver_fee(int deliver_fee) {
        this.deliver_fee = deliver_fee;
    }

    public int getSend_price() {
        return send_price;
    }

    public void setSend_price(int send_price) {
        this.send_price = send_price;
    }
}
