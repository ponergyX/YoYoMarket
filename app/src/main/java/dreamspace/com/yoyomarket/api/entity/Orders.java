package dreamspace.com.yoyomarket.api.entity;

import java.util.ArrayList;

import dreamspace.com.yoyomarket.api.entity.element.OrderItem;

/**
 * Created by Lx on 2016/2/28.
 */
public class Orders {
    private ArrayList<OrderItem> result;

    public ArrayList<OrderItem> getResult() {
        return result;
    }

    public void setResult(ArrayList<OrderItem> result) {
        this.result = result;
    }
}
