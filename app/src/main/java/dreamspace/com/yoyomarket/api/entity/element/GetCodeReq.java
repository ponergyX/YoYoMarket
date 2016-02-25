package dreamspace.com.yoyomarket.api.entity.element;

/**
 * Created by Lx on 2016/2/15.
 */
public class GetCodeReq {
    private String phone_num;
    private int operation;//0注册，1重设密码

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }
}
