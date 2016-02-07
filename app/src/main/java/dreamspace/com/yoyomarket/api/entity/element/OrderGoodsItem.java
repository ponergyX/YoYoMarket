package dreamspace.com.yoyomarket.api.entity.element;

/**
 * Created by Lx on 2016/1/30.
 */
public class OrderGoodsItem {
    private String name;
    private int quantity;
    private double price;
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
