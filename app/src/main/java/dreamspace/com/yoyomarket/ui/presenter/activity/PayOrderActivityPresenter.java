package dreamspace.com.yoyomarket.ui.presenter.activity;

import javax.inject.Inject;

import dreamspace.com.yoyomarket.ui.presenter.Presenter;
import dreamspace.com.yoyomarket.ui.view.View;
import dreamspace.com.yoyomarket.ui.view.activity.PayOrderView;
import dreamspace.com.yoyomarket.ui.views.activity.PayOrderActivity;

/**
 * Created by Lx on 2016/2/24.
 */
public class PayOrderActivityPresenter implements Presenter {
    private PayOrderView payOrderView;

    @Inject
    public PayOrderActivityPresenter(){

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
        payOrderView = (PayOrderView) v;
    }
}
