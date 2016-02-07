package dreamspace.com.yoyomarket.ui.presenter.activity;

import javax.inject.Inject;

import dreamspace.com.yoyomarket.ui.presenter.Presenter;
import dreamspace.com.yoyomarket.ui.view.View;
import dreamspace.com.yoyomarket.ui.view.activity.ModifyPwdView;

/**
 * Created by Lx on 2016/2/3.
 */
public class ModifyPwdActivityPresenter implements Presenter{
    private ModifyPwdView modifyPwdView;

    @Inject
    public ModifyPwdActivityPresenter(){

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
        modifyPwdView = (ModifyPwdView) v;
    }
}
