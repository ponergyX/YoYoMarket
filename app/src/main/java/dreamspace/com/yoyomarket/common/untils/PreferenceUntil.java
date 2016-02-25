package dreamspace.com.yoyomarket.common.untils;

import android.content.Context;
import android.content.SharedPreferences;

import com.orhanobut.logger.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

import dreamspace.com.yoyomarket.common.qualifier.PreferenceType;
import dreamspace.com.yoyomarket.injector.qualifier.ForApplication;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Lx on 2016/2/14.
 */
public class PreferenceUntil {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String APP = "dreamspace.com.yoyomarket";

    public PreferenceUntil(Context context){
        sharedPreferences = context.getSharedPreferences(APP,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public Observable<String> getAvatar(){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String avatar = sharedPreferences.getString(PreferenceType.AVATAR,"");
                subscriber.onStart();
                subscriber.onNext(avatar);
                subscriber.onCompleted();
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> getPhone(){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String phone = sharedPreferences.getString(PreferenceType.PHONE,"");
                subscriber.onStart();
                subscriber.onNext(phone);
                subscriber.onCompleted();
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> getAccessToken(){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String access = sharedPreferences.getString(PreferenceType.ACCESS_TOKEN,"");
                Logger.d(access.length()+"");
                subscriber.onStart();
                subscriber.onNext(access);
                subscriber.onCompleted();
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    }

    public void putString(final String s,@PreferenceType final String type){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                editor.putString(type,s);
                editor.commit();
                subscriber.onStart();
                subscriber.onNext("");
                subscriber.onCompleted();
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        });
    }
}
