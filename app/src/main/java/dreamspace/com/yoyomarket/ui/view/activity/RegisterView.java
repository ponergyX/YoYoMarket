package dreamspace.com.yoyomarket.ui.view.activity;

import android.support.annotation.NonNull;

import dreamspace.com.yoyomarket.ui.view.View;

/**
 * Created by Lx on 2016/2/2.
 */
public interface RegisterView extends View{
    void showNetCantUse();

    void showNetError();

    void checkCode();

    void showToast(@NonNull String s);
}
