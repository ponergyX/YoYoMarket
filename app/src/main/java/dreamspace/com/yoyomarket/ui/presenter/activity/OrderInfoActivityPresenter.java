package dreamspace.com.yoyomarket.ui.presenter.activity;

import android.content.Context;

import javax.inject.Inject;

import dreamspace.com.yoyomarket.api.entity.element.OrderInfo;
import dreamspace.com.yoyomarket.common.untils.NetUntils;
import dreamspace.com.yoyomarket.injector.qualifier.ForApplication;
import dreamspace.com.yoyomarket.model.OrderModel;
import dreamspace.com.yoyomarket.ui.presenter.Presenter;
import dreamspace.com.yoyomarket.ui.view.View;
import dreamspace.com.yoyomarket.ui.view.activity.OrderInfoView;
import retrofit2.HttpException;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Lx on 2016/2/26.
 */
public class OrderInfoActivityPresenter implements Presenter{
    private OrderInfoView orderInfoView;
    private Context appContext;
    private OrderModel orderModel;
    private Subscription getOrderInfoSubscription;

    @Inject
    public OrderInfoActivityPresenter(@ForApplication Context context,OrderModel orderModel){
        appContext = context;
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
        if(getOrderInfoSubscription != null){
            getOrderInfoSubscription.unsubscribe();
        }
    }

    @Override
    public void attchView(View v) {
        orderInfoView = (OrderInfoView) v;
    }

    public void getOrderInfo(String orderId){
        if(!NetUntils.isNetworkAvailable(appContext)){
            orderInfoView.showErrorViewState();
            orderInfoView.showNetCantUse();
            return;
        }

        getOrderInfoSubscription = orderModel.getOrderInfo(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        orderInfoView.showLoading();
                    }
                })
                .subscribe(new Action1<OrderInfo>() {
                    @Override
                    public void call(OrderInfo orderInfo) {
                        orderInfoView.showNormal(orderInfo);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        orderInfoView.showErrorViewState();
                        if(throwable instanceof HttpException){
                            HttpException exception = (HttpException) throwable;
                            orderInfoView.showToast(exception.getMessage());
                        }else {
                            orderInfoView.showNetError();
                        }
                    }
                });
    }
}
