package dreamspace.com.yoyomarket.api.entity;

import java.util.ArrayList;

import dreamspace.com.yoyomarket.api.entity.element.MarketCatalog;

/**
 * Created by Lx on 2016/1/30.
 */
public class MarketCatalogs {
    private ArrayList<String> catalog;

    public ArrayList<String> getCatalog() {
        return catalog;
    }

    public void setCatalog(ArrayList<String> catalog) {
        this.catalog = catalog;
    }
}
