package dreamspace.com.yoyomarket.ui.view.fragment;

import dreamspace.com.yoyomarket.ui.view.View;

/**
 * Created by Lx on 2016/1/28.
 */
public interface MineView extends View{
    void showNetCantUse();

    void showToast(String s);

    void showNetError();

    void showUploadProcess();

    void showUploadError();

    void showUploadSuccess(String path);
}
