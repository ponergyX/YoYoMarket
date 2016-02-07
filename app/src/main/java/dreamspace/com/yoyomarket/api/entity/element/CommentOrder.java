package dreamspace.com.yoyomarket.api.entity.element;

/**
 * Created by Lx on 2016/1/30.
 */
public class CommentOrder {
    private double score;
    private String order_id;
    private String content;

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
