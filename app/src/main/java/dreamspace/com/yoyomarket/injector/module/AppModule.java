package dreamspace.com.yoyomarket.injector.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dreamspace.com.yoyomarket.common.App;
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
}
