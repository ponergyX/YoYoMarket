package dreamspace.com.yoyomarket.ui.presenter.activity;

import javax.inject.Inject;

import dreamspace.com.yoyomarket.ui.presenter.Presenter;
import dreamspace.com.yoyomarket.ui.view.View;
import dreamspace.com.yoyomarket.ui.view.activity.AddressView;

/**
 * Created by Lx on 2016/2/3.
 */
public class AddressActivityPresenter implements Presenter {
    private AddressView addressView;

    @Inject
    public AddressActivityPresenter(){

    }

    @Override
    public void initInjector() {

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
        addressView = (AddressView) v;
    }
}
