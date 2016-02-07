package dreamspace.com.yoyomarket.ui.presenter.activity;

import android.app.Activity;

import javax.inject.Inject;

import dreamspace.com.yoyomarket.ui.presenter.Presenter;
import dreamspace.com.yoyomarket.ui.view.View;
import dreamspace.com.yoyomarket.ui.view.activity.MainView;
import dreamspace.com.yoyomarket.ui.views.activity.MainActivity;

/**
 * Created by Lx on 2016/1/28.
 */
public class MainActivityPresenter implements Presenter{
    private MainView mainView;

    @Inject
    public MainActivityPresenter(){

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
        mainView = (MainView)v;
    }
}
