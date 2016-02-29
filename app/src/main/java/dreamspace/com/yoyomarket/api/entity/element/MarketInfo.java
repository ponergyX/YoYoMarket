package dreamspace.com.yoyomarket.api.entity.element;

/**
 * Created by Lx on 2016/1/28.
 */
public class MarketInfo {
    private String name;
    private String image;
    private String notice;
    private int score;

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

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
