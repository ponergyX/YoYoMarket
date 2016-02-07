package dreamspace.com.yoyomarket.injector.component;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dreamspace.com.yoyomarket.common.App;
import dreamspace.com.yoyomarket.common.Navigator;
import dreamspace.com.yoyomarket.injector.module.AppModule;

/**
 * Created by Lx on 2016/1/27.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    Navigator navigator();
}
