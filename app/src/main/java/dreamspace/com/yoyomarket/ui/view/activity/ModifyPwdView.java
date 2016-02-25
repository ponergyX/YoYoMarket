package dreamspace.com.yoyomarket.ui.view.activity;

import android.support.annotation.NonNull;

import dreamspace.com.yoyomarket.ui.view.View;

/**
 * Created by Lx on 2016/2/3.
 */
public interface ModifyPwdView extends View{
    void showNetCantUse();

    void showNetError();

    void showToast(@NonNull String s);

    void showProcessDialog();

    void showModifyError(String s);

    void showModifySuccess();
}
