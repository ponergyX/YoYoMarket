package dreamspace.com.yoyomarket.ui.view.activity;

import android.support.annotation.NonNull;


import dreamspace.com.yoyomarket.api.entity.element.OrderInfo;
import dreamspace.com.yoyomarket.ui.view.View;

/**
 * Created by Lx on 2016/2/26.
 */
public interface OrderInfoView extends View{
    void showNetCantUse();

    void showNetError();

    void showErrorViewState();

    void showToast(@NonNull String s);

    void showLoading();

    void showNormal(OrderInfo orderInfo);
}
