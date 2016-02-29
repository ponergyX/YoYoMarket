package dreamspace.com.yoyomarket.common.event;

import java.util.HashMap;

import dreamspace.com.yoyomarket.api.entity.element.GoodInfo;

/**
 * Created by Lx on 2016/2/23.
 */
public class GoodsListPickGoodsChangeEvent {
    private HashMap<String,GoodInfo> pickGoods;
    public GoodsListPickGoodsChangeEvent(HashMap<String, GoodInfo> pickGoods){
        this.pickGoods = pickGoods;
    }

    public HashMap<String,GoodInfo> getPickGoods() {
        return pickGoods;
    }
}
