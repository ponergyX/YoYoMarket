package dreamspace.com.yoyomarket.api.entity.element;

/**
 * Created by Lx on 2016/2/21.
 */
public class ModifyPwdReq {
    private String phone_num;
    private String password;
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
