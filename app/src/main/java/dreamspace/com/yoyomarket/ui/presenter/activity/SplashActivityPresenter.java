package dreamspace.com.yoyomarket.ui.presenter.activity;

import android.os.Handler;

import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import dreamspace.com.yoyomarket.common.provider.UserTokenProvider;
import dreamspace.com.yoyomarket.common.untils.PreferenceUntil;
import dreamspace.com.yoyomarket.model.GoodsModel;
import dreamspace.com.yoyomarket.model.MarketModel;
import dreamspace.com.yoyomarket.model.OrderModel;
import dreamspace.com.yoyomarket.model.TokenModel;
import dreamspace.com.yoyomarket.model.UserModel;
import dreamspace.com.yoyomarket.ui.presenter.Presenter;
import dreamspace.com.yoyomarket.ui.view.View;
import dreamspace.com.yoyomarket.ui.view.activity.SplashView;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Lx on 2016/2/21.
 */
public class SplashActivityPresenter implements Presenter {
    private SplashView splashView;
    private PreferenceUntil preferenceUntil;
    private TokenModel tokenModel;
    private UserModel userModel;
    private MarketModel marketModel;
    private GoodsModel goodsModel;
    private OrderModel orderModel;
    private Subscription preferenceSubsription;

    @Inject
    public SplashActivityPresenter(PreferenceUntil preferenceUntil,TokenModel tokenModel,UserModel userModel,
                                   MarketModel marketModel,GoodsModel goodsModel,OrderModel orderModel){
        this.preferenceUntil = preferenceUntil;
        this.tokenModel = tokenModel;
        this.userModel = userModel;
        this.orderModel = orderModel;
        this.marketModel = marketModel;
        this.goodsModel = goodsModel;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onCreate() {
        preferenceSubsription = preferenceUntil.getAccessToken()
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if(s.length() > 0){
                            tokenModel.setTokenProvider(new UserTokenProvider(s));
                            userModel.setTokenProvider(new UserTokenProvider(s));
                            marketModel.setTokenProvider(new UserTokenProvider(s));
                            goodsModel.setTokenProvider(new UserTokenProvider(s));
                            orderModel.setTokenProvider(new UserTokenProvider(s));
                        }
                    }
                });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                splashView.navigateToMainAct();
            }
        },3000);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestory() {
        if(preferenceSubsription != null){
            preferenceSubsription.unsubscribe();
        }
    }

    @Override
    public void attchView(View v) {
        splashView = (SplashView) v;
    }
}
