package dreamspace.com.yoyomarket.common;

import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;

import dreamspace.com.yoyomarket.injector.component.ApiComponent;
import dreamspace.com.yoyomarket.injector.component.AppComponent;
import dreamspace.com.yoyomarket.injector.component.DaggerApiComponent;
import dreamspace.com.yoyomarket.injector.component.DaggerAppComponent;
import dreamspace.com.yoyomarket.injector.module.AppModule;

/**
 * Created by Lx on 2016/1/26.
 */
public class App extends Application {
    private AppComponent appComponent;

    private ApiComponent apiComponent;

    private AppModule appModule;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        Logger.init()
            .logLevel(LogLevel.FULL)
            .hideThreadInfo()
            .methodCount(0);

        appModule = new AppModule(this);
        initInjectorApp();
        initInjectorApi();
    }

    private void initInjectorApp(){
        appComponent = DaggerAppComponent.builder()
                .appModule(appModule)
                .build();
    }

    private void initInjectorApi(){
        apiComponent = DaggerApiComponent.builder()
                .appModule(appModule)
                .build();
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }

    public ApiComponent getApiComponent(){
        return apiComponent;
    }
}
