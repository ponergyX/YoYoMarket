package dreamspace.com.yoyomarket.ui.presenter.activity;

import android.content.Context;
import android.os.Looper;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.inject.Inject;

import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.api.entity.Goods;
import dreamspace.com.yoyomarket.api.entity.MarketCatalogs;
import dreamspace.com.yoyomarket.api.entity.element.Commodity;
import dreamspace.com.yoyomarket.api.entity.element.GoodInfo;
import dreamspace.com.yoyomarket.common.untils.NetUntils;
import dreamspace.com.yoyomarket.injector.qualifier.ForApplication;
import dreamspace.com.yoyomarket.model.GoodsModel;
import dreamspace.com.yoyomarket.model.MarketModel;
import dreamspace.com.yoyomarket.ui.presenter.Presenter;
import dreamspace.com.yoyomarket.ui.view.View;
import dreamspace.com.yoyomarket.ui.view.activity.PickGoodsView;
import retrofit2.HttpException;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Lx on 2016/2/4.
 */
public class PickGoodsActivityPresenter implements Presenter{
    private PickGoodsView pickGoodsView;
    private MarketModel marketModel;
    private GoodsModel goodsModel;
    private Context appContext;
    private Subscription getCatalogsSubscription;
    private Subscription getGoodsSubsription;

    private HashMap<String,ArrayList<GoodInfo>> goods = new HashMap<>();

    @Inject
    public PickGoodsActivityPresenter(@ForApplication Context context,MarketModel marketModel,GoodsModel goodsModel){
        this.marketModel = marketModel;
        this.goodsModel = goodsModel;
        appContext = context;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestory() {
        if(getCatalogsSubscription != null){
            getCatalogsSubscription.unsubscribe();
        }

        if(getGoodsSubsription != null){
            getGoodsSubsription.unsubscribe();
        }
    }

    @Override
    public void attchView(View v) {
        pickGoodsView = (PickGoodsView) v;
    }

    public void loadData(final String supId){
        if(!NetUntils.isNetworkAvailable(appContext)){
            pickGoodsView.showErrorViewState();
            pickGoodsView.showNetCantUse();
            return;
        }

        getCatalogsSubscription = marketModel.getCatalogs(supId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        pickGoodsView.showLoading();
                    }
                })
                .flatMap(new Func1<MarketCatalogs, Observable<String>>() {
                    @Override
                    public Observable<String> call(MarketCatalogs marketCatalogs) {
                        ArrayList<String> catalogList = new ArrayList<>();
                        catalogList.add("");
                        catalogList.addAll(marketCatalogs.getCatalog());
                        return Observable.from(catalogList)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .flatMap(new Func1<String, Observable<Commodity>>() {
                    @Override
                    public Observable<Commodity> call(String s) {
                        if(s.equals(""))
                        {
                            goods.put(appContext.getString(R.string.sale_count_rank),null);
                        }else{
                            goods.put(s,null);
                        }
                        return goodsModel.getGoodsList(supId,s)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .subscribe(new Action1<Commodity>() {
                    @Override
                    public void call(Commodity commodity) {
                        if(commodity.getCatalog().equals("")){
                            goods.put(appContext.getString(R.string.sale_count_rank),commodity.getGoods().getResult());
                        }else{
                            goods.put(commodity.getCatalog(), commodity.getGoods().getResult());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if(throwable instanceof HttpException){
                            HttpException exception = (HttpException) throwable;
                            pickGoodsView.showToast(exception.getMessage());
                            pickGoodsView.showErrorViewState();
                        }else{
                            pickGoodsView.showErrorViewState();
                            pickGoodsView.showNetError();
                        }
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        pickGoodsView.showNormal(goods);
                    }
                });
    }
}
