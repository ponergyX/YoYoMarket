package dreamspace.com.yoyomarket.ui.presenter.activity;

import android.content.Context;

import com.google.gson.JsonElement;

import java.util.ArrayList;

import javax.inject.Inject;

import dreamspace.com.yoyomarket.api.entity.element.CommonStatusRes;
import dreamspace.com.yoyomarket.api.entity.element.CreateOrderInfo;
import dreamspace.com.yoyomarket.api.entity.element.CreateOrderRes;
import dreamspace.com.yoyomarket.api.entity.element.DeliverTime;
import dreamspace.com.yoyomarket.api.entity.element.OrderChannel;
import dreamspace.com.yoyomarket.common.untils.NetUntils;
import dreamspace.com.yoyomarket.injector.qualifier.ForApplication;
import dreamspace.com.yoyomarket.model.MarketModel;
import dreamspace.com.yoyomarket.model.OrderModel;
import dreamspace.com.yoyomarket.ui.presenter.Presenter;
import dreamspace.com.yoyomarket.ui.view.View;
import dreamspace.com.yoyomarket.ui.view.activity.PayOrderView;
import retrofit2.HttpException;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Lx on 2016/2/24.
 */
public class PayOrderActivityPresenter implements Presenter {
    private PayOrderView payOrderView;
    private Context appContext;
    private MarketModel marketModel;
    private OrderModel orderModel;
    private Subscription getTimeSubscription;
    private Subscription createOrderSubscription;

    @Inject
    public PayOrderActivityPresenter(@ForApplication Context context,MarketModel marketModel,OrderModel orderModel){
        appContext = context;
        this.marketModel = marketModel;
        this.orderModel = orderModel;
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
        if(getTimeSubscription != null){
            getTimeSubscription.unsubscribe();
        }

        if(createOrderSubscription != null){
            createOrderSubscription.unsubscribe();
        }
    }

    @Override
    public void attchView(View v) {
        payOrderView = (PayOrderView) v;
    }

    public void getDeliverTimes(){
        if(!NetUntils.isNetworkAvailable(appContext)){
            payOrderView.showErrorViewState();
            payOrderView.showNetCantUse();
            return;
        }

        getTimeSubscription = marketModel.getDeliverTimes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        payOrderView.showLoading();
                    }
                })
                .subscribe(new Action1<ArrayList<DeliverTime>>() {
                    @Override
                    public void call(ArrayList<DeliverTime> deliverTimes) {
                        if (deliverTimes.size() > 0) {
                            payOrderView.showNormal(deliverTimes);
                        } else {
                            payOrderView.showEmpty();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof HttpException) {
                            HttpException exception = (HttpException) throwable;
                            payOrderView.showErrorViewState();
                            payOrderView.showToast(exception.getMessage());
                        } else {
                            payOrderView.showErrorViewState();
                            payOrderView.showNetError();
                        }
                    }
                });
    }

    public void createOrder(final CreateOrderInfo orderInfo, final OrderChannel orderChannel){
        if(!NetUntils.isNetworkAvailable(appContext)){
            payOrderView.showNetCantUse();
            return;
        }

        createOrderSubscription = orderModel.createOrder(orderInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        payOrderView.showOrderProcess();
                    }
                })
                .flatMap(new Func1<CreateOrderRes, Observable<JsonElement>>() {
                    @Override
                    public Observable<JsonElement> call(CreateOrderRes createOrderRes) {
                        return orderModel.payOrder(createOrderRes.getOrder_id(), orderChannel)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        payOrderView.showOrderSuccess(jsonElement.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if(throwable instanceof HttpException){
                            HttpException exception = (HttpException) throwable;
                            payOrderView.showToast(exception.getMessage());
                        }else{
                            payOrderView.showNetError();
                        }
                        payOrderView.showOrderFail();
                    }
                });
    }
}
