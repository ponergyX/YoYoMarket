package dreamspace.com.yoyomarket.api.entity;

/**
 * Created by Lx on 2016/2/22.
 */
public class UserInfo {
    private String image;
    private String name;
    private String enroll_year;
    private String location;
    private int weixin_bind;
    private int weibo_bind;
    private String phone_num;

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

    public String getEnroll_year() {
        return enroll_year;
    }

    public void setEnroll_year(String enroll_year) {
        this.enroll_year = enroll_year;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getWeixin_bind() {
        return weixin_bind;
    }

    public void setWeixin_bind(int weixin_bind) {
        this.weixin_bind = weixin_bind;
    }

    public int getWeibo_bind() {
        return weibo_bind;
    }

    public void setWeibo_bind(int weibo_bind) {
        this.weibo_bind = weibo_bind;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }
}
