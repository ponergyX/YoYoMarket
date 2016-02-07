package dreamspace.com.yoyomarket.injector.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import dreamspace.com.yoyomarket.injector.scope.PerActivity;

/**
 * Created by Lx on 2016/1/27.
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity){
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity provideActivity(){
        return activity;
    }
}
