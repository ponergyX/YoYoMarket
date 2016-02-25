package dreamspace.com.yoyomarket.ui.view.activity;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import dreamspace.com.yoyomarket.api.entity.element.AddressesListItem;
import dreamspace.com.yoyomarket.common.qualifier.GetDataType;
import dreamspace.com.yoyomarket.ui.view.View;

/**
 * Created by Lx on 2016/2/3.
 */
public interface AddressView extends View{
    void showNetCantUse();

    void showNetError();

    void showErrorViewState();

    void showToast(@NonNull String s);

    void showNoAddress();

    void showLoading();

    void showNormal(ArrayList<AddressesListItem> addresses,@GetDataType int type);

    void showError(String s);

    void showDeleteAddressDialog(String addressId);

    void showDeleteAddressConfirmDialog(String addressId);

    void showDeleteAddressProcess();

    void showDeleteError(String s);

    void showDeleteSuccess();

    void setLoadMoreFinish();
}
