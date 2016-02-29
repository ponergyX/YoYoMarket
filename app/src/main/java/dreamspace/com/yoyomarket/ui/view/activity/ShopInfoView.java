package dreamspace.com.yoyomarket.ui.view.activity;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import dreamspace.com.yoyomarket.api.entity.element.DeliverTime;
import dreamspace.com.yoyomarket.api.entity.element.MarketInfo;
import dreamspace.com.yoyomarket.ui.view.View;

/**
 * Created by Lx on 2016/2/6.
 */
public interface ShopInfoView extends View{
    void showNetCantUse();

    void showNetError();

    void showToast(@NonNull String s);

    void showErrorViewState();

    void showLoading();

    void showNormal(MarketInfo marketInfo);
}
