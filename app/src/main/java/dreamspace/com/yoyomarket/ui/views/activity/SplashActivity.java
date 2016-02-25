package dreamspace.com.yoyomarket.ui.views.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import dreamspace.com.yoyomarket.common.base.BaseActivity;
import dreamspace.com.yoyomarket.ui.presenter.activity.SplashActivityPresenter;
import dreamspace.com.yoyomarket.ui.view.activity.SplashView;

/**
 * Created by Lx on 2016/2/21.
 */
public class SplashActivity extends BaseActivity implements SplashView{

    @Inject
    SplashActivityPresenter splashActivityPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjector();
        initPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        splashActivityPresenter.onDestory();
    }

    private void initInjector(){
        getApiComponent().inject(this);
    }

    private void initPresenter(){
        splashActivityPresenter.attchView(this);
        splashActivityPresenter.onCreate();
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    public void navigateToMainAct() {
        navigator.navigateToMainActivity(this);
        finish();
    }
}
