package dreamspace.com.yoyomarket.ui.view.activity;

import android.support.annotation.NonNull;

import dreamspace.com.yoyomarket.ui.view.View;

/**
 * Created by Lx on 2016/2/3.
 */
public interface ModifyAddressView extends View{
    void showNetCantUse();

    void showNetError();

    void showToast(@NonNull String s);

    void showCreateProcess();

    void showModifyProcess();

    void showCreateError(String s);

    void showModifyError(String s);

    void showCreateSuccess();

    void showModifySuccess();
}
