package dreamspace.com.yoyomarket.ui.presenter.activity;

import javax.inject.Inject;

import dreamspace.com.yoyomarket.ui.presenter.Presenter;
import dreamspace.com.yoyomarket.ui.view.View;
import dreamspace.com.yoyomarket.ui.view.activity.FeedbackView;

/**
 * Created by Lx on 2016/2/4.
 */
public class FeedbackActivityPresenter implements Presenter{
    private FeedbackView feedbackView;

    @Inject
    public FeedbackActivityPresenter(){

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
        feedbackView = (FeedbackView) v;
    }
}
