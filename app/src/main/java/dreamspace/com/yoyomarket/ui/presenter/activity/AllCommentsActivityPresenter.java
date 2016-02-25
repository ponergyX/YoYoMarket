package dreamspace.com.yoyomarket.ui.presenter.activity;

import javax.inject.Inject;

import dreamspace.com.yoyomarket.ui.presenter.Presenter;
import dreamspace.com.yoyomarket.ui.view.View;
import dreamspace.com.yoyomarket.ui.view.activity.AllCommentsView;

/**
 * Created by Lx on 2016/2/6.
 */
public class AllCommentsActivityPresenter implements Presenter{
    private AllCommentsView allCommentsView;

    @Inject
    public AllCommentsActivityPresenter(){

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
        allCommentsView = (AllCommentsView) v;
    }
}
