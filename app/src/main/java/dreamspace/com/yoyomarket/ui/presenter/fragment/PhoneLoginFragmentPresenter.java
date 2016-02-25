package dreamspace.com.yoyomarket.ui.presenter.fragment;

import android.content.Context;

import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.api.entity.element.CommonStatusRes;
import dreamspace.com.yoyomarket.api.entity.element.LoginRes;
import dreamspace.com.yoyomarket.common.provider.UserTokenProvider;
import dreamspace.com.yoyomarket.common.qualifier.PreferenceType;
import dreamspace.com.yoyomarket.common.untils.NetUntils;
import dreamspace.com.yoyomarket.common.untils.PreferenceUntil;
import dreamspace.com.yoyomarket.injector.qualifier.ForApplication;
import dreamspace.com.yoyomarket.model.GoodsModel;
import dreamspace.com.yoyomarket.model.MarketModel;
import dreamspace.com.yoyomarket.model.OrderModel;
import dreamspace.com.yoyomarket.model.TokenModel;
import dreamspace.com.yoyomarket.model.UserModel;
import dreamspace.com.yoyomarket.ui.presenter.Presenter;
import dreamspace.com.yoyomarket.ui.view.View;
import dreamspace.com.yoyomarket.ui.view.fragment.PhoneLoginView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Lx on 2016/2/2.
 */
public class PhoneLoginFragmentPresenter implements Presenter{
    private PhoneLoginView phoneLoginView;
    private TokenModel tokenModel;
    private UserModel userModel;
    private MarketModel marketModel;
    private GoodsModel goodsModel;
    private OrderModel orderModel;
    private Context appContext;
    private PreferenceUntil preferenceUntil;
    private Subscription getCodeSubscription;
    private Subscription loginSubscription;

    @Inject
    public PhoneLoginFragmentPresenter(@ForApplication Context appContext,TokenModel tokenModel,UserModel userModel,MarketModel marketModel,
                                       GoodsModel goodsModel,OrderModel orderModel,PreferenceUntil preferenceUntil){
        this.appContext = appContext;
        this.tokenModel = tokenModel;
        this.userModel = userModel;
        this.marketModel = marketModel;
        this.goodsModel = goodsModel;
        this.orderModel = orderModel;
        this.preferenceUntil = preferenceUntil;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onCreate() {
        preferenceUntil.getPhone()
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if(s.length() > 0){
                            phoneLoginView.setTextInPhoneEt(s);
                        }
                    }
                });
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

        if(loginSubscription != null){
            loginSubscription.unsubscribe();
        }
    }

    @Override
    public void attchView(View v) {
        phoneLoginView = (PhoneLoginView) v;
    }

    public void getCode(String phoneNum,int operation){
        if(!NetUntils.isNetworkAvailable(appContext)){
            phoneLoginView.showNetCantUse();
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
                            phoneLoginView.showToast(exception.getMessage());
                        }else{
                            phoneLoginView.showNetError();
                        }
                    }
                });
    }

    public void login(final String phoneNum,String code){
        if(!NetUntils.isNetworkAvailable(appContext)){
            phoneLoginView.showNetCantUse();
            return;
        }

        loginSubscription = tokenModel.codeLogin(phoneNum,code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        phoneLoginView.showLoginProcessDialog();
                    }
                })
                .subscribe(new Action1<LoginRes>() {
                    @Override
                    public void call(LoginRes loginRes) {
                        if(loginRes.getAccess_token() != null){
                            preferenceUntil.putString(loginRes.getAccess_token(), PreferenceType.ACCESS_TOKEN);
                            preferenceUntil.putString(phoneNum,PreferenceType.PHONE);
                            tokenModel.setTokenProvider(new UserTokenProvider(loginRes.getAccess_token()));
                            userModel.setTokenProvider(new UserTokenProvider(loginRes.getAccess_token()));
                            marketModel.setTokenProvider(new UserTokenProvider(loginRes.getAccess_token()));
                            goodsModel.setTokenProvider(new UserTokenProvider(loginRes.getAccess_token()));
                            orderModel.setTokenProvider(new UserTokenProvider(loginRes.getAccess_token()));
                            phoneLoginView.loginSuccess();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if(throwable instanceof retrofit2.HttpException){
                            retrofit2.HttpException exception = (retrofit2.HttpException)throwable;
                            phoneLoginView.loginError(exception.getMessage());
//                            normalLoginView.showToast(exception.getMessage());
                        }else{
                            phoneLoginView.loginError(appContext.getString(R.string.net_error));
//                            normalLoginView.showNetError();
                        }
                    }
                });
    }
}
