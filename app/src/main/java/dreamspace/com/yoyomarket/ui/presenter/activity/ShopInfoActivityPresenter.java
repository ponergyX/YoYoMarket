package dreamspace.com.yoyomarket.ui.presenter.activity;

import android.content.Context;

import javax.inject.Inject;

import dreamspace.com.yoyomarket.common.untils.NetUntils;
import dreamspace.com.yoyomarket.injector.qualifier.ForApplication;
import dreamspace.com.yoyomarket.model.MarketModel;
import dreamspace.com.yoyomarket.ui.presenter.Presenter;
import dreamspace.com.yoyomarket.ui.view.View;
import dreamspace.com.yoyomarket.ui.view.activity.ShopInfoView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Lx on 2016/2/6.
 */
public class ShopInfoActivityPresenter implements Presenter{
    private ShopInfoView shopInfoView;
    private Context appContext;
    private MarketModel marketModel;
    private Subscription getMarketInfoSubscription;

    @Inject
    public ShopInfoActivityPresenter(@ForApplication Context context,MarketModel marketModel){
        appContext = context;
        this.marketModel = marketModel;
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

    }

    @Override
    public void attchView(View v) {
        shopInfoView = (ShopInfoView) v;
    }

    public void getMarketInfo(String supId){
        if(!NetUntils.isNetworkAvailable(appContext)){
            shopInfoView.showErrorViewState();
            shopInfoView.showNetCantUse();
            return;
        }

        getMarketInfoSubscription = marketModel.getMarketInfo(supId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        shopInfoView.showLoading();
                    }
                }).subscribe();
    }
}
