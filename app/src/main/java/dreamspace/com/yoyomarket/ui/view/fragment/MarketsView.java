package dreamspace.com.yoyomarket.ui.view.fragment;

import dreamspace.com.yoyomarket.ui.view.View;

/**
 * Created by Lx on 2016/1/28.
 */
public interface MarketsView extends View{
    void showLoading();

    void showError();

    void showEmpty();

    void showNormal();
}
