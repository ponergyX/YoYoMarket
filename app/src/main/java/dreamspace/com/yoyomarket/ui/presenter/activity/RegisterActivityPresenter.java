package dreamspace.com.yoyomarket.ui.presenter.activity;

import android.content.Context;



import javax.inject.Inject;

import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.api.entity.element.CommonStatusRes;
import dreamspace.com.yoyomarket.api.entity.element.RegisterRes;
import dreamspace.com.yoyomarket.common.base.BaseActivity;
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
import dreamspace.com.yoyomarket.ui.view.activity.RegisterView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Lx on 2016/2/2.
 */
public class RegisterActivityPresenter implements Presenter{
    private RegisterView registerView;
    private TokenModel tokenModel;
    private UserModel userModel;
    private MarketModel marketModel;
    private GoodsModel goodsModel;
    private OrderModel orderModel;
    private Context appContext;
    private Subscription getCodeSubscription;
    private Subscription registerSubscription;
    private PreferenceUntil preferenceUntil;
    private BaseActivity activity;

    @Inject
    public RegisterActivityPresenter(@ForApplication Context context,TokenModel tokenModel,UserModel userModel
            ,GoodsModel goodsModel,MarketModel marketModel,OrderModel orderModel,PreferenceUntil preferenceUntil){
        this.tokenModel = tokenModel;
        this.userModel = userModel;
        this.marketModel = marketModel;
        this.goodsModel = goodsModel;
        this.orderModel = orderModel;
        appContext = context;
        this.preferenceUntil = preferenceUntil;
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

        if(registerSubscription != null){
            registerSubscription.unsubscribe();
        }
    }

    @Override
    public void attchView(View v) {

    }

    public void attchView(View v,BaseActivity activity) {
        registerView = (RegisterView) v;
        this.activity = activity;
    }

    public void getCode(String phoneNum,int operation){
        if(!NetUntils.isNetworkAvailable(appContext)){
            registerView.showNetCantUse();
            return;
        }

        getCodeSubscription = tokenModel.getCode(phoneNum, operation)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CommonStatusRes>() {
                    @Override
                    public void call(CommonStatusRes commonStatusRes) {
                        if (commonStatusRes.getStatus().equals("ok")) {
                            registerView.checkCode();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if(throwable instanceof retrofit2.HttpException){
                            retrofit2.HttpException exception = (retrofit2.HttpException)throwable;
                            if(exception.code() == 406){
                                registerView.showToast(exception.getMessage());
                            }
                        }else{
                            registerView.showNetError();
                        }
                    }
                });
    }

    public void register(String phoneNum,String pwd,String code){
        if(!NetUntils.isNetworkAvailable(appContext)){
            registerView.showNetCantUse();
            return;
        }

        registerSubscription = tokenModel.register(phoneNum,pwd,code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RegisterRes>() {
                    @Override
                    public void call(RegisterRes registerRes) {
                        if(registerRes.getAccess_token() != null){
                            registerView.showToast(appContext.getString(R.string.register_success));
                            preferenceUntil.putString(registerRes.getAccess_token(), PreferenceType.ACCESS_TOKEN);
                            tokenModel.setTokenProvider(new UserTokenProvider(registerRes.getAccess_token()));
                            userModel.setTokenProvider(new UserTokenProvider(registerRes.getAccess_token()));
                            marketModel.setTokenProvider(new UserTokenProvider(registerRes.getAccess_token()));
                            goodsModel.setTokenProvider(new UserTokenProvider(registerRes.getAccess_token()));
                            orderModel.setTokenProvider(new UserTokenProvider(registerRes.getAccess_token()));
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if(throwable instanceof retrofit2.HttpException){
                            retrofit2.HttpException exception = (retrofit2.HttpException)throwable;
                            registerView.showToast(exception.getMessage());
                        }else{
                            registerView.showNetError();
                        }
                    }
                });
    }
}
