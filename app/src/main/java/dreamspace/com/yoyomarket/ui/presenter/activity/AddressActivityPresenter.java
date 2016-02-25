package dreamspace.com.yoyomarket.ui.presenter.activity;

import android.content.Context;

import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.api.entity.Addresses;
import dreamspace.com.yoyomarket.api.entity.element.CommonStatusRes;
import dreamspace.com.yoyomarket.common.qualifier.GetDataType;
import dreamspace.com.yoyomarket.common.untils.NetUntils;
import dreamspace.com.yoyomarket.injector.qualifier.ForApplication;
import dreamspace.com.yoyomarket.model.UserModel;
import dreamspace.com.yoyomarket.ui.presenter.Presenter;
import dreamspace.com.yoyomarket.ui.view.View;
import dreamspace.com.yoyomarket.ui.view.activity.AddressView;
import retrofit2.HttpException;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Lx on 2016/2/3.
 */
public class AddressActivityPresenter implements Presenter {
    private AddressView addressView;
    private Context appContext;
    private UserModel userModel;
    private Subscription getAddressesSubscription;
    private Subscription deleteAddressSubscription;
    private int page = 0;

    @Inject
    public AddressActivityPresenter(@ForApplication Context context,UserModel userModel){
        this.userModel = userModel;
        appContext = context;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestory() {
        if(getAddressesSubscription != null){
            getAddressesSubscription.unsubscribe();
        }

        if(deleteAddressSubscription != null){
            deleteAddressSubscription.unsubscribe();
        }
    }

    @Override
    public void attchView(View v) {
        addressView = (AddressView) v;
    }

    public void getAddress(){
        if(!NetUntils.isNetworkAvailable(appContext)){
            if(page == 0){
                addressView.showErrorViewState();
            }
            addressView.showNetCantUse();
            addressView.setLoadMoreFinish();
            return;
        }

        getAddressesSubscription = userModel.getAddressList(++page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Addresses>() {
                    @Override
                    public void call(Addresses addresses) {
                        if(addresses.getResult() != null){
                            if(page == 1 && addresses.getResult().size() == 0){
                                addressView.showNoAddress();
                            }

                            if(addresses.getResult().size() > 0){
                                if(page == 1){
                                    addressView.showNormal(addresses.getResult(), GetDataType.FIRST_GET_DATA);
                                }else{
                                    addressView.showNormal(addresses.getResult(), GetDataType.LOAD_MORE);
                                }
                            }
                            addressView.setLoadMoreFinish();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        addressView.setLoadMoreFinish();
                        if(page == 1){
                            addressView.showErrorViewState();
                        }

                        if(throwable instanceof HttpException){
                            HttpException exception = (HttpException) throwable;
                            addressView.showError(exception.getMessage());
                        }else{
                            addressView.showNetError();
                        }
                    }
                });
    }

    public void getFreshData(){
        page = 0;
        getAddress();
    }

    public void deleteAddress(String addressId){
        if(!NetUntils.isNetworkAvailable(appContext)){
            addressView.showNetCantUse();
            return;
        }

        deleteAddressSubscription = userModel.deleteAddress(addressId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        addressView.showDeleteAddressProcess();
                    }
                })
                .subscribe(new Action1<CommonStatusRes>() {
                    @Override
                    public void call(CommonStatusRes commonStatusRes) {
                        if(commonStatusRes.getStatus().equals("ok")){
                            addressView.showDeleteSuccess();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.d(throwable.toString());
                        if(throwable instanceof HttpException){
                            HttpException exception = (HttpException) throwable;
                            addressView.showDeleteError(exception.getMessage());
                        }else{
                            addressView.showDeleteError(appContext.getString(R.string.net_error));
                        }
                    }
                });
    }
}
