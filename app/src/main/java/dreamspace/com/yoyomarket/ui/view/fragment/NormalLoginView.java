package dreamspace.com.yoyomarket.ui.view.fragment;

import dreamspace.com.yoyomarket.ui.view.View;

/**
 * Created by Lx on 2016/2/2.
 */
public interface NormalLoginView extends View{
    void showNetCantUse();

    void showToast(String s);

    void showNetError();

    void showLoginProcessDialog();

    void hideLoginProcessDialog();

    void loginError(String s);

    void loginSuccess();

    void setTextInPhoneEt(String s);
}
