package dreamspace.com.yoyomarket.ui.presenter.fragment;

import android.app.Activity;
import android.content.Context;

import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.api.entity.Markets;
import dreamspace.com.yoyomarket.common.qualifier.GetDataType;
import dreamspace.com.yoyomarket.common.untils.NetUntils;
import dreamspace.com.yoyomarket.injector.qualifier.ForApplication;
import dreamspace.com.yoyomarket.model.MarketModel;
import dreamspace.com.yoyomarket.ui.presenter.Presenter;
import dreamspace.com.yoyomarket.ui.view.View;
import dreamspace.com.yoyomarket.ui.view.fragment.MarketsView;
import retrofit2.HttpException;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Lx on 2016/1/28.
 */
public class MarketsFragmentPresenter implements Presenter{
    private MarketsView marketsView;
    private Context appContext;
    private MarketModel marketModel;
    private Subscription getMarketListSubscription;
    private int page = 0;

    @Inject
    public MarketsFragmentPresenter(@ForApplication Context context,MarketModel marketModel){
        this.marketModel = marketModel;
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
        if(getMarketListSubscription != null){
            getMarketListSubscription.unsubscribe();
        }
    }

    @Override
    public void attchView(View v) {
        marketsView = (MarketsView) v;
    }

    public void getMarketList(){
        if(!NetUntils.isNetworkAvailable(appContext)){
            if(page == 0){
                marketsView.showErrorViewState();
            }
            marketsView.showNetCantUse();
            marketsView.setLoadMoreFinish();
        }

        getMarketListSubscription = marketModel.getMarketsList(++page,appContext.getString(R.string.location))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Markets>() {
                    @Override
                    public void call(Markets markets) {
                        if (markets.getResult() != null) {
                            if (markets.getResult().size() == 0 && page == 1) {
                                marketsView.showEmpty();
                            }

                            if (markets.getResult().size() > 0) {
                                if (page == 1) {
                                    marketsView.showNormal(markets.getResult(), GetDataType.FIRST_GET_DATA);
                                } else {
                                    marketsView.showNormal(markets.getResult(), GetDataType.LOAD_MORE);
                                }
                            }

                            marketsView.setLoadMoreFinish();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.d(throwable.toString());
                        if (page == 1) {
                            marketsView.showErrorViewState();
                        }

                        if (throwable instanceof HttpException) {
                            HttpException exception = (HttpException) throwable;
                            marketsView.showToast(exception.getMessage());
                        } else {
                            marketsView.showNetError();
                        }
                        marketsView.setLoadMoreFinish();
                    }
                });
    }

    public void getFreshData(){
        page = 0;
        getMarketList();
    }
}
