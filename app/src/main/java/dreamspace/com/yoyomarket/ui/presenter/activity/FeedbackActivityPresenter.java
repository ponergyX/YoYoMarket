package dreamspace.com.yoyomarket.ui.presenter.activity;

import android.content.Context;

import javax.inject.Inject;

import dreamspace.com.yoyomarket.injector.qualifier.ForApplication;
import dreamspace.com.yoyomarket.model.UserModel;
import dreamspace.com.yoyomarket.ui.presenter.Presenter;
import dreamspace.com.yoyomarket.ui.view.View;
import dreamspace.com.yoyomarket.ui.view.activity.FeedbackView;
import rx.Subscription;

/**
 * Created by Lx on 2016/2/4.
 */
public class FeedbackActivityPresenter implements Presenter{
    private FeedbackView feedbackView;
    private Context appContext;
    private UserModel userModel;
    private Subscription feedbackSubscription;

    @Inject
    public FeedbackActivityPresenter(@ForApplication Context context,UserModel userModel){
        this.userModel = userModel;
        appContext = context;
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
        if(feedbackSubscription != null){
            feedbackSubscription.unsubscribe();
        }
    }

    @Override
    public void attchView(View v) {
        feedbackView = (FeedbackView) v;
    }
}
