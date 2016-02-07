package dreamspace.com.yoyomarket.ui.presenter.fragment;

import javax.inject.Inject;

import dreamspace.com.yoyomarket.ui.presenter.Presenter;
import dreamspace.com.yoyomarket.ui.view.View;
import dreamspace.com.yoyomarket.ui.view.fragment.PhoneLoginView;

/**
 * Created by Lx on 2016/2/2.
 */
public class PhoneLoginFragmentPresenter implements Presenter{
    private PhoneLoginView phoneLoginView;

    @Inject
    public PhoneLoginFragmentPresenter(){

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
        phoneLoginView = (PhoneLoginView) v;
    }
}
