package dreamspace.com.yoyomarket.model;

import android.support.annotation.Nullable;

import java.util.ArrayList;

import dreamspace.com.yoyomarket.api.MarketApi;
import dreamspace.com.yoyomarket.api.entity.MarketCatalogs;
import dreamspace.com.yoyomarket.api.entity.Markets;
import dreamspace.com.yoyomarket.api.entity.element.DeliverTime;
import dreamspace.com.yoyomarket.api.entity.element.MarketInfo;
import dreamspace.com.yoyomarket.common.base.BaseModel;
import dreamspace.com.yoyomarket.common.provider.TokenProvider;
import rx.Observable;

/**
 * Created by Lx on 2016/1/30.
 */
public class MarketModel extends BaseModel<MarketApi>{
    public MarketModel(@Nullable TokenProvider tokenProvider) {
        super(tokenProvider);
    }

    @Override
    protected Class<MarketApi> getServiceClass() {
        return MarketApi.class;
    }

    public Observable<Markets> getMarketsList(int page,String location){
        return getService().getMarketsList(page,location);
    }

    public Observable<MarketCatalogs> getCatalogs(String supId){
        return getService().getMarketCatalogs(supId);
    }

    public Observable<ArrayList<DeliverTime>> getDeliverTimes(){
        return getService().getDeliverTimes();
    }

    public Observable<MarketInfo> getMarketInfo(String supId){
        return getService().getMarketInfo(supId);
    }
}
