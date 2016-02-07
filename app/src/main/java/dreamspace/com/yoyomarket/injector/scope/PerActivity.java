package dreamspace.com.yoyomarket.injector.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Lx on 2016/1/27.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity{
}
