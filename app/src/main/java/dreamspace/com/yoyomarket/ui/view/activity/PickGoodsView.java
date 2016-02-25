package dreamspace.com.yoyomarket.ui.view.activity;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

import dreamspace.com.yoyomarket.api.entity.element.GoodInfo;
import dreamspace.com.yoyomarket.ui.view.View;

/**
 * Created by Lx on 2016/2/4.
 */
public interface PickGoodsView extends View{
    void showNetCantUse();

    void showNetError();

    void showToast(@NonNull String s);

    void showErrorViewState();

    void showLoading();

    void showNormal(HashMap<String,ArrayList<GoodInfo>> goods);
}
