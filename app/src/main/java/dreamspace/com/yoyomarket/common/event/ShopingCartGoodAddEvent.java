package dreamspace.com.yoyomarket.common.event;

import java.util.HashMap;

import dreamspace.com.yoyomarket.api.entity.element.GoodInfo;

/**
 * Created by Lx on 2016/2/23.
 */
public class ShopingCartGoodAddEvent {
    private HashMap<GoodInfo,Integer> pickGoods;

    public ShopingCartGoodAddEvent(HashMap<GoodInfo,Integer> pickGoods){
        this.pickGoods = pickGoods;
    }

    public HashMap<GoodInfo, Integer> getPickGoods() {
        return pickGoods;
    }

    public void setPickGoods(HashMap<GoodInfo, Integer> pickGoods) {
        this.pickGoods = pickGoods;
    }
}
