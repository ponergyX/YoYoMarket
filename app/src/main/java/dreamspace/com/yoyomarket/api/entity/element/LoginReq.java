package dreamspace.com.yoyomarket.api.entity.element;

/**
 * Created by Lx on 2016/2/15.
 */
public class LoginReq {
    private String phone_num;
    private String password;

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
