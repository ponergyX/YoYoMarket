package dreamspace.com.yoyomarket.ui.presenter.activity;

import android.content.Context;

import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.api.entity.element.CommonStatusRes;
import dreamspace.com.yoyomarket.api.entity.element.UserIdRes;
import dreamspace.com.yoyomarket.common.untils.NetUntils;
import dreamspace.com.yoyomarket.injector.qualifier.ForApplication;
import dreamspace.com.yoyomarket.model.TokenModel;
import dreamspace.com.yoyomarket.ui.presenter.Presenter;
import dreamspace.com.yoyomarket.ui.view.View;
import dreamspace.com.yoyomarket.ui.view.activity.ModifyPwdView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Lx on 2016/2/3.
 */
public class ModifyPwdActivityPresenter implements Presenter{
    private ModifyPwdView modifyPwdView;
    private Context appContext;
    private TokenModel tokenModel;
    private Subscription getCodeSubscription;
    private Subscription modifySubscription;

    @Inject
    public ModifyPwdActivityPresenter(@ForApplication Context context,TokenModel tokenModel){
        this.appContext = context;
        this.tokenModel = tokenModel;
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
        if(getCodeSubscription != null){
            getCodeSubscription.unsubscribe();
        }

        if(modifySubscription != null){
            modifySubscription.unsubscribe();
        }
    }

    @Override
    public void attchView(View v) {
        modifyPwdView = (ModifyPwdView) v;
    }

    public void getCode(String phoneNum,int operation){
        if(!NetUntils.isNetworkAvailable(appContext)){
            modifyPwdView.showNetCantUse();
            return;
        }

        getCodeSubscription = tokenModel.getCode(phoneNum, operation)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CommonStatusRes>() {
                    @Override
                    public void call(CommonStatusRes commonStatusRes) {
                        if (commonStatusRes.getStatus().equals("ok")) {

                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if(throwable instanceof retrofit2.HttpException){
                            retrofit2.HttpException exception = (retrofit2.HttpException)throwable;
                            modifyPwdView.showToast(exception.getMessage());
                        }else{
                            modifyPwdView.showNetError();
                        }
                    }
                });
    }

    public void modifyPassword(String phoneNum,String code,String pwd){
        if(!NetUntils.isNetworkAvailable(appContext)){
            modifyPwdView.showNetCantUse();
            return;
        }

        modifySubscription = tokenModel.modifyPwd(phoneNum,code,pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        modifyPwdView.showProcessDialog();
                    }
                })
                .subscribe(new Action1<UserIdRes>() {
                    @Override
                    public void call(UserIdRes userIdRes) {
                        if(userIdRes.getUser_id() != null){
                            modifyPwdView.showModifySuccess();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if(throwable instanceof retrofit2.HttpException){
                            retrofit2.HttpException exception = (retrofit2.HttpException)throwable;
                            modifyPwdView.showModifyError(exception.getMessage());
                        }else{
                            modifyPwdView.showModifyError(appContext.getString(R.string.net_error));
                        }
                    }
                });
    }
}
