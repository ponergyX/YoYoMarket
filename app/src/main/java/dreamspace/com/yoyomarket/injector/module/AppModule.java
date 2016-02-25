package dreamspace.com.yoyomarket.injector.module;

import android.accounts.AccountManager;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dreamspace.com.yoyomarket.common.App;
import dreamspace.com.yoyomarket.common.untils.PreferenceUntil;
import dreamspace.com.yoyomarket.common.untils.UploadImageUntil;
import dreamspace.com.yoyomarket.injector.qualifier.ForApplication;

/**
 * Created by Lx on 2016/1/27.
 */
@Module
public class AppModule {
    private final App app;

    public AppModule(App app){
        this.app = app;
    }

    @Provides
    @Singleton
    @ForApplication
    Context provideAppContext(){
        return app;
    }

    @Provides
    @Singleton
    PreferenceUntil providePreferenceUntil(@ForApplication Context context){
        return new PreferenceUntil(context);
    }

    @Provides
    @Singleton
    UploadImageUntil provideUploadImageUntil(){
        return new UploadImageUntil();
    }
}
