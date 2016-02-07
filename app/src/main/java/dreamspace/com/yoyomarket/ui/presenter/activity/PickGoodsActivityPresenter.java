package dreamspace.com.yoyomarket.ui.presenter.activity;

import javax.inject.Inject;

import dreamspace.com.yoyomarket.ui.presenter.Presenter;
import dreamspace.com.yoyomarket.ui.view.View;
import dreamspace.com.yoyomarket.ui.view.activity.PickGoodsView;

/**
 * Created by Lx on 2016/2/4.
 */
public class PickGoodsActivityPresenter implements Presenter{
    private PickGoodsView pickGoodsView;

    @Inject
    public PickGoodsActivityPresenter(){

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
        pickGoodsView = (PickGoodsView) v;
    }
}
