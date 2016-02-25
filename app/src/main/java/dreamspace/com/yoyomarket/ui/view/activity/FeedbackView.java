package dreamspace.com.yoyomarket.ui.view.activity;

import android.support.annotation.NonNull;

import dreamspace.com.yoyomarket.ui.view.View;

/**
 * Created by Lx on 2016/2/4.
 */
public interface FeedbackView extends View{
    void showNetCantUse();

    void showNetError();

    void showToast(@NonNull String s);

    void showSuggestError(String s);

    void showSuggestSuccess();

    void showSuggestProcess();
}
