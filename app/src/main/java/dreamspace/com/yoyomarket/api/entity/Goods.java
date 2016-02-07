package dreamspace.com.yoyomarket.api.entity;

import java.util.ArrayList;

import dreamspace.com.yoyomarket.api.entity.element.GoodInfo;

/**
 * Created by Lx on 2016/1/28.
 */
public class Goods {
    private ArrayList<GoodInfo> result;

    public ArrayList<GoodInfo> getResult() {
        return result;
    }

    public void setResult(ArrayList<GoodInfo> result) {
        this.result = result;
    }
}
