package dreamspace.com.yoyomarket.ui.presenter.fragment;

import android.app.Activity;
import android.content.Context;

import com.orhanobut.logger.Logger;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;

import org.json.JSONObject;

import javax.inject.Inject;

import dreamspace.com.yoyomarket.api.entity.UserInfo;
import dreamspace.com.yoyomarket.api.entity.element.CommonStatusRes;
import dreamspace.com.yoyomarket.api.entity.element.ImageTokenRes;
import dreamspace.com.yoyomarket.common.qualifier.PreferenceType;
import dreamspace.com.yoyomarket.common.untils.NetUntils;
import dreamspace.com.yoyomarket.common.untils.PreferenceUntil;
import dreamspace.com.yoyomarket.common.untils.UploadImageUntil;
import dreamspace.com.yoyomarket.injector.qualifier.ForApplication;
import dreamspace.com.yoyomarket.model.UserModel;
import dreamspace.com.yoyomarket.ui.presenter.Presenter;
import dreamspace.com.yoyomarket.ui.view.View;
import dreamspace.com.yoyomarket.ui.view.fragment.MineView;
import retrofit2.HttpException;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Lx on 2016/1/28.
 */
public class MineFragmentPresenter implements Presenter{
    private MineView mineView;
    private UserModel userModel;
    private UploadImageUntil uploadImageUntil;
    private Context appConetxt;
    private Subscription uploadImageSubcription;
    private PreferenceUntil preferenceUntil;

    @Inject
    public MineFragmentPresenter(@ForApplication Context context,UserModel userModel,UploadImageUntil uploadImageUntil,
                                 PreferenceUntil preferenceUntil){
        this.userModel = userModel;
        this.uploadImageUntil = uploadImageUntil;
        appConetxt = context;
        this.preferenceUntil = preferenceUntil;
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
        if(uploadImageSubcription != null){
            uploadImageSubcription.unsubscribe();
        }
    }

    @Override
    public void attchView(View v) {
        mineView = (MineView) v;
    }

    public void uploadImage(final String path){
        if(!NetUntils.isNetworkAvailable(appConetxt)){
            mineView.showNetCantUse();
            return;
        }

        uploadImageSubcription = userModel.getUploadImageToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mineView.showUploadProcess();
                    }
                })
                .subscribe(new Action1<ImageTokenRes>() {
                    @Override
                    public void call(ImageTokenRes imageTokenRes) {
                        uploadImageUntil.upLoadImage(path, imageTokenRes.getKey(), imageTokenRes.getToken()
                                , new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject response) {
                                if (info.isOK()) {
                                    updateUserAvatar(key);
                                }else{
                                    mineView.showUploadError();
                                }
                            }
                        }, null);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof retrofit2.HttpException) {
                            HttpException exception = (HttpException) throwable;
                            mineView.showUploadError();
                            mineView.showToast(exception.getMessage());
                        } else {
                            mineView.showUploadError();
                            mineView.showNetError();
                        }
                    }
                });
    }

    public void updateUserAvatar(String key){
        if(!NetUntils.isNetworkAvailable(appConetxt)){
            mineView.showNetCantUse();
            return;
        }

        userModel.updateUserAvatar(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<CommonStatusRes, Observable<UserInfo>>() {
                    @Override
                    public Observable<UserInfo> call(CommonStatusRes commonStatusRes) {
                        return userModel.getUserInfo()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .subscribe(new Action1<UserInfo>() {
                    @Override
                    public void call(UserInfo userInfo) {
                        if (userInfo.getImage() != null) {
                            mineView.showUploadSuccess(userInfo.getImage());
                            //cache avatar
                            preferenceUntil.putString(userInfo.getImage(), PreferenceType.AVATAR);

                        } else {
                            mineView.showUploadError();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof retrofit2.HttpException) {
                            HttpException exception = (HttpException) throwable;
                            mineView.showUploadError();
                            mineView.showToast(exception.getMessage());
                            Logger.d(exception.getMessage());
                        } else {
                            mineView.showUploadError();
                            mineView.showNetError();
                            Logger.d(throwable.toString());
                        }
                    }
                });
    }
}
