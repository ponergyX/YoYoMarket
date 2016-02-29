package dreamspace.com.yoyomarket.ui.presenter.fragment;

import android.app.Activity;
import android.content.Context;

import javax.inject.Inject;

import dreamspace.com.yoyomarket.api.entity.Orders;
import dreamspace.com.yoyomarket.common.qualifier.GetDataType;
import dreamspace.com.yoyomarket.common.untils.NetUntils;
import dreamspace.com.yoyomarket.injector.qualifier.ForApplication;
import dreamspace.com.yoyomarket.model.OrderModel;
import dreamspace.com.yoyomarket.ui.presenter.Presenter;
import dreamspace.com.yoyomarket.ui.view.View;
import dreamspace.com.yoyomarket.ui.view.fragment.OrdersView;
import retrofit2.HttpException;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Lx on 2016/1/28.
 */
public class OrdersFragmentPresenter implements Presenter{
    private OrdersView ordersView;
    private Context appContext;
    private OrderModel orderModel;
    private Subscription getOrderListSubscription;
    private int page = 0;

    @Inject
    public OrdersFragmentPresenter(@ForApplication Context context,OrderModel orderModel){
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
        if(getOrderListSubscription != null){
            getOrderListSubscription.unsubscribe();
        }
    }

    @Override
    public void attchView(View v) {
        ordersView = (OrdersView) v;
    }

    public void getOrderList(){
        if(!NetUntils.isNetworkAvailable(appContext)){
            if(page == 0){
                ordersView.showErrorViewState();
            }
            ordersView.showNetCantUse();
            ordersView.setLoadMoreFinish();
        }

        getOrderListSubscription = orderModel.getOrderList(++page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Orders>() {
                    @Override
                    public void call(Orders orders) {
                        if(orders.getResult() != null){
                            if(orders.getResult().size() == 0 && page == 1){
                                ordersView.showEmpty();
                            }

                            if(orders.getResult().size() > 0){
                                if(page == 1){
                                    ordersView.showNormal(orders.getResult(), GetDataType.FIRST_GET_DATA);
                                }else{
                                    ordersView.showNormal(orders.getResult(),GetDataType.LOAD_MORE);
                                }
                            }
                        }
                            ordersView.setLoadMoreFinish();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if(page == 1){
                            ordersView.showErrorViewState();
                        }

                        if(throwable instanceof HttpException){
                            HttpException exception = (HttpException) throwable;
                            ordersView.showToast(exception.getMessage());
                        }else{
                            ordersView.showNetError();
                        }

                        ordersView.setLoadMoreFinish();
                    }
                });
    }

    public void getFreshData(){
        page = 0;
        getOrderList();
    }
}
