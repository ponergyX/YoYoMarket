package dreamspace.com.yoyomarket.api.entity.element;

import dreamspace.com.yoyomarket.api.entity.Goods;

/**
 * Created by Lx on 2016/2/23.
 */
public class Commodity {
    private String catalog;
    private Goods goods;

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }
}
