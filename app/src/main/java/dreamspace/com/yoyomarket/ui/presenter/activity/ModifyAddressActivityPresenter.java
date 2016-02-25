package dreamspace.com.yoyomarket.ui.presenter.activity;

import android.content.Context;

import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.api.entity.element.AddressId;
import dreamspace.com.yoyomarket.api.entity.element.CommonStatusRes;
import dreamspace.com.yoyomarket.common.untils.NetUntils;
import dreamspace.com.yoyomarket.injector.qualifier.ForApplication;
import dreamspace.com.yoyomarket.model.UserModel;
import dreamspace.com.yoyomarket.ui.presenter.Presenter;
import dreamspace.com.yoyomarket.ui.view.View;
import dreamspace.com.yoyomarket.ui.view.activity.ModifyAddressView;
import retrofit2.HttpException;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Lx on 2016/2/3.
 */
public class ModifyAddressActivityPresenter implements Presenter{
    private ModifyAddressView modifyAddressView;
    private Context appContext;
    private UserModel userModel;
    private Subscription createAddressSubscription;
    private Subscription modifyAddressSubscription;

    @Inject
    public ModifyAddressActivityPresenter(@ForApplication Context context,UserModel userModel){
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
        if(createAddressSubscription != null){
            createAddressSubscription.unsubscribe();
        }

        if(modifyAddressSubscription != null){
            modifyAddressSubscription.unsubscribe();
        }
    }

    @Override
    public void attchView(View v) {
        modifyAddressView = (ModifyAddressView) v;
    }

    public void createAddress(String phoneNum,String address,String sex,String name){
        if(!NetUntils.isNetworkAvailable(appContext)){
            modifyAddressView.showNetCantUse();
            return;
        }

        createAddressSubscription = userModel.createAddress(phoneNum,address,sex,name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        modifyAddressView.showCreateProcess();
                    }
                })
                .subscribe(new Action1<AddressId>() {
                    @Override
                    public void call(AddressId addressId) {
                        if(addressId.getAddress_id() != null){
                            modifyAddressView.showCreateSuccess();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.d(throwable.toString());
                        if(throwable instanceof HttpException)
                        {
                            HttpException exception = (HttpException) throwable;
                            modifyAddressView.showCreateError(exception.getMessage());
                        }else{
                            modifyAddressView.showCreateError(appContext.getString(R.string.net_error));
                        }
                    }
                });
    }

    public void modifyAddress(String phoneNum,String address,String sex,String name,String addressId){
        if(!NetUntils.isNetworkAvailable(appContext)){
            modifyAddressView.showNetCantUse();
            return;
        }

        modifyAddressSubscription = userModel.modifyAddress(phoneNum, address, sex, name, addressId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        modifyAddressView.showModifyProcess();
                    }
                })
                .subscribe(new Action1<CommonStatusRes>() {
                    @Override
                    public void call(CommonStatusRes commonStatusRes) {
                        if(commonStatusRes.getStatus().equals("ok")){
                            modifyAddressView.showModifySuccess();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if(throwable instanceof HttpException)
                        {
                            Logger.d(throwable.toString());
                            HttpException exception = (HttpException) throwable;
                            modifyAddressView.showModifyError(exception.getMessage());
                        }else{
                            modifyAddressView.showModifyError(appContext.getString(R.string.net_error));
                        }
                    }
                });
    }
}
