package dreamspace.com.yoyomarket.api.entity.element;

/**
 * Created by Lx on 2016/1/28.
 */
public class GoodInfo {
    private String goods_id;
    private String image;
    private String name;
    private String sales_number;
    private double price;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSales_number() {
        return sales_number;
    }

    public void setSales_number(String sales_number) {
        this.sales_number = sales_number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
