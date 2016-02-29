package dreamspace.com.yoyomarket.ui.presenter.activity;

import android.content.Context;

import javax.inject.Inject;

import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.api.entity.element.SuggestionReq;
import dreamspace.com.yoyomarket.api.entity.element.SuggestionRes;
import dreamspace.com.yoyomarket.common.untils.NetUntils;
import dreamspace.com.yoyomarket.injector.qualifier.ForApplication;
import dreamspace.com.yoyomarket.model.UserModel;
import dreamspace.com.yoyomarket.ui.presenter.Presenter;
import dreamspace.com.yoyomarket.ui.view.View;
import dreamspace.com.yoyomarket.ui.view.activity.FeedbackView;
import retrofit2.HttpException;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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

    public void feedback(SuggestionReq suggestionReq){
        if(!NetUntils.isNetworkAvailable(appContext)){
            feedbackView.showNetCantUse();
            return;
        }

        feedbackSubscription = userModel.suggest(suggestionReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        feedbackView.showSuggestProcess();
                    }
                })
                .subscribe(new Action1<SuggestionRes>() {
                    @Override
                    public void call(SuggestionRes suggestionRes) {
                        feedbackView.showSuggestSuccess();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if(throwable instanceof HttpException){
                            HttpException exception = (HttpException) throwable;
                            feedbackView.showSuggestError(exception.getMessage());
                        }else{
                            feedbackView.showSuggestError(appContext.getString(R.string.net_error));
                        }
                    }
                });
    }
}
