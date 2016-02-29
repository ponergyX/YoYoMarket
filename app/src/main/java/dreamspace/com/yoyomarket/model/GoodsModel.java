package dreamspace.com.yoyomarket.model;

import android.support.annotation.Nullable;

import dreamspace.com.yoyomarket.api.GoodsApi;
import dreamspace.com.yoyomarket.api.entity.Goods;
import dreamspace.com.yoyomarket.api.entity.element.Commodity;
import dreamspace.com.yoyomarket.common.base.BaseModel;
import dreamspace.com.yoyomarket.common.provider.TokenProvider;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Lx on 2016/1/30.
 */
public class GoodsModel extends BaseModel<GoodsApi>{
    public GoodsModel(@Nullable TokenProvider tokenProvider) {
        super(tokenProvider);
    }

    @Override
    protected Class<GoodsApi> getServiceClass() {
        return GoodsApi.class;
    }

    public Observable<Commodity> getGoodsList(String supId, final String catalog){
        return getService().getGoodsList(supId,catalog).flatMap(new Func1<Goods, Observable<Commodity>>() {
            @Override
            public Observable<Commodity> call(Goods goods) {
                final Commodity commodity = new Commodity();
                commodity.setGoods(goods);
                commodity.setCatalog(catalog);
                return Observable.create(new Observable.OnSubscribe<Commodity>() {
                    @Override
                    public void call(Subscriber<? super Commodity> subscriber) {
                        subscriber.onStart();
                        subscriber.onNext(commodity);
                        subscriber.onCompleted();
                    }
                });
            }
        });
    }

    public Observable<Goods> searchGoods(String supId,String keyword){
        return getService().searchShopGoods(supId,keyword);
    }
}
