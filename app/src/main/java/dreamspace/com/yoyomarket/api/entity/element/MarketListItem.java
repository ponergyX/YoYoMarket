package dreamspace.com.yoyomarket.api.entity.element;

/**
 * Created by Lx on 2016/1/28.
 */
public class MarketListItem {
    private String sup_id;
    private String name;
    private String image;
    private String sales_number;
    private double score;

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

    public String getSales_number() {
        return sales_number;
    }

    public void setSales_number(String sales_number) {
        this.sales_number = sales_number;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
