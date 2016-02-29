package dreamspace.com.yoyomarket.ui.view.fragment;

import java.util.ArrayList;

import dreamspace.com.yoyomarket.api.entity.element.MarketListItem;
import dreamspace.com.yoyomarket.api.entity.element.OrderItem;
import dreamspace.com.yoyomarket.common.qualifier.GetDataType;
import dreamspace.com.yoyomarket.ui.view.View;

/**
 * Created by Lx on 2016/1/28.
 */
public interface OrdersView extends View{
    void showNetCantUse();

    void showToast(String s);

    void showNetError();

    void showLoading();

    void showErrorViewState();

    void showEmpty();

    void showNormal(ArrayList<OrderItem> markets,@GetDataType int type);

    void setLoadMoreFinish();

    void showCancelOrderProcess();

    void showCancelOrderFailed();

    void showCancelOrderSuccess();
}
