package dreamspace.com.yoyomarket.ui.presenter;

import android.app.Activity;

import dreamspace.com.yoyomarket.ui.view.View;

/**
 * Created by Lx on 2016/1/27.
 */
public interface Presenter {
    void onStart();

    void onCreate();

    void onPause();

    void onStop();

    void onDestory();

    void attchView(View v);
}
