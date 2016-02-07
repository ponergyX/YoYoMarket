package dreamspace.com.yoyomarket.ui.presenter.fragment;

import android.app.Activity;

import javax.inject.Inject;

import dreamspace.com.yoyomarket.ui.presenter.Presenter;
import dreamspace.com.yoyomarket.ui.view.View;
import dreamspace.com.yoyomarket.ui.view.fragment.MineView;

/**
 * Created by Lx on 2016/1/28.
 */
public class MineFragmentPresenter implements Presenter{
    private MineView mineView;

    @Inject
    public MineFragmentPresenter(){

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
        mineView = (MineView) v;
    }
}
