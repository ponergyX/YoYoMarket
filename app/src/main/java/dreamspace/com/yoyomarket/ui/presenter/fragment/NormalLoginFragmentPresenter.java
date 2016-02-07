package dreamspace.com.yoyomarket.ui.presenter.fragment;

import javax.inject.Inject;

import dreamspace.com.yoyomarket.ui.presenter.Presenter;
import dreamspace.com.yoyomarket.ui.view.View;
import dreamspace.com.yoyomarket.ui.view.fragment.NormalLoginView;

/**
 * Created by Lx on 2016/2/2.
 */
public class NormalLoginFragmentPresenter implements Presenter{
    private NormalLoginView normalLoginView;

    @Inject
    public NormalLoginFragmentPresenter(){

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
        normalLoginView = (NormalLoginView) v;
    }
}
