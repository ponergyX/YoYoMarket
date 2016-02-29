package dreamspace.com.yoyomarket.injector.component;

import javax.inject.Singleton;

import dagger.Component;
import dreamspace.com.yoyomarket.injector.module.ApiModule;
import dreamspace.com.yoyomarket.ui.presenter.activity.MainActivityPresenter;
import dreamspace.com.yoyomarket.ui.views.activity.AddressActivity;
import dreamspace.com.yoyomarket.ui.views.activity.AllCommentsActivity;
import dreamspace.com.yoyomarket.ui.views.activity.FeedbackActivity;
import dreamspace.com.yoyomarket.ui.views.activity.MainActivity;
import dreamspace.com.yoyomarket.ui.views.activity.ModifyAddressActivity;
import dreamspace.com.yoyomarket.ui.views.activity.ModifyPwdActivity;
import dreamspace.com.yoyomarket.ui.views.activity.OrderInfoActivity;
import dreamspace.com.yoyomarket.ui.views.activity.PayOrderActivity;
import dreamspace.com.yoyomarket.ui.views.activity.PickGoodsActivity;
import dreamspace.com.yoyomarket.ui.views.activity.RegisterActivity;
import dreamspace.com.yoyomarket.ui.views.activity.SearchGoodActivity;
import dreamspace.com.yoyomarket.ui.views.activity.ShopInfoActivity;
import dreamspace.com.yoyomarket.ui.views.activity.SplashActivity;
import dreamspace.com.yoyomarket.ui.views.fragment.MarketsFragment;
import dreamspace.com.yoyomarket.ui.views.fragment.MineFragment;
import dreamspace.com.yoyomarket.ui.views.fragment.NormalLoginFragment;
import dreamspace.com.yoyomarket.ui.views.fragment.OrdersFragment;
import dreamspace.com.yoyomarket.ui.views.fragment.PhoneLoginFragment;

/**
 * Created by Lx on 2016/1/27.
 */
@Singleton
@Component(modules = ApiModule.class)
public interface ApiComponent {
    void inject(MainActivity mainActivity);

    void inject(MarketsFragment marketsFragment);

    void inject(OrdersFragment ordersFragment);

    void inject(MineFragment mineFragment);

    void inject(PhoneLoginFragment phoneLoginFragment);

    void inject(NormalLoginFragment normalLoginFragment);

    void inject(RegisterActivity registerActivity);

    void inject(ModifyPwdActivity modifyPwdActivity);

    void inject(AddressActivity addressActivity);

    void inject(ModifyAddressActivity modifyAddressActivity);

    void inject(FeedbackActivity feedbackActivity);

    void inject(PickGoodsActivity pickGoodsActivity);

    void inject(ShopInfoActivity shopInfoActivity);

    void inject(AllCommentsActivity allCommentsActivity);

    void inject(SplashActivity splashActivity);

    void inject(SearchGoodActivity searchGoodActivity);

    void inject(PayOrderActivity payOrderActivity);

    void inject(OrderInfoActivity orderInfoActivity);
}
