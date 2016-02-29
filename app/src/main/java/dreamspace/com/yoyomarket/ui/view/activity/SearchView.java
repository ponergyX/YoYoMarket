package dreamspace.com.yoyomarket.ui.view.activity;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import dreamspace.com.yoyomarket.api.entity.element.GoodInfo;
import dreamspace.com.yoyomarket.ui.view.View;

/**
 * Created by Lx on 2016/2/24.
 */
public interface SearchView extends View{
    void showNetCantUse();

    void showNetError();

    void showErrorViewState();

    void showToast(@NonNull String s);

    void showNormal(ArrayList<GoodInfo> goods);

    void notFindResult();

    void showLoading();
}
