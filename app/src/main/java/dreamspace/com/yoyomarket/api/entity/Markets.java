package dreamspace.com.yoyomarket.api.entity;

import java.util.ArrayList;

import dreamspace.com.yoyomarket.api.entity.element.MarketListItem;

/**
 * Created by Lx on 2016/1/28.
 */
public class Markets {
    private ArrayList<MarketListItem> result;

    public ArrayList<MarketListItem> getResult() {
        return result;
    }

    public void setResult(ArrayList<MarketListItem> result) {
        this.result = result;
    }
}
