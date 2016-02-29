package dreamspace.com.yoyomarket.common.event;

import java.util.HashMap;

import dreamspace.com.yoyomarket.api.entity.element.GoodInfo;

/**
 * Created by Lx on 2016/2/25.
 */
public class SearchCartPickGoodsChangeEvent {
    private HashMap<String,GoodInfo> pickGoods;

    public SearchCartPickGoodsChangeEvent(HashMap<String,GoodInfo> pickGoods){
        this.pickGoods = pickGoods;
    }

    public HashMap<String, GoodInfo> getPickGoods() {
        return pickGoods;
    }
}
