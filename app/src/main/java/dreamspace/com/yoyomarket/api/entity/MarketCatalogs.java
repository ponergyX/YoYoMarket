package dreamspace.com.yoyomarket.api.entity;

import java.util.ArrayList;

import dreamspace.com.yoyomarket.api.entity.element.MarketCatalog;

/**
 * Created by Lx on 2016/1/30.
 */
public class MarketCatalogs {
    private ArrayList<MarketCatalog> group;

    public ArrayList<MarketCatalog> getGroup() {
        return group;
    }

    public void setGroup(ArrayList<MarketCatalog> group) {
        this.group = group;
    }
}
