package dreamspace.com.yoyomarket.injector.component;

import android.app.Activity;

import dagger.Component;
import dreamspace.com.yoyomarket.injector.module.ActivityModule;
import dreamspace.com.yoyomarket.injector.module.AppModule;
import dreamspace.com.yoyomarket.injector.scope.PerActivity;

/**
 * Created by Lx on 2016/1/27.
 */
@PerActivity
@Component(
        dependencies = AppComponent.class,
        modules = ActivityModule.class
)
public interface ActivityComponent {
    Activity activity();
}
