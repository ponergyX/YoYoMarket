package dreamspace.com.yoyomarket.common.qualifier;/**
 * Created by Lx on 2016/2/18.
 */

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        ChangePasswordType.REGISTER,
        ChangePasswordType.MODIFY_PASSWORD
})

public @interface ChangePasswordType{
    int REGISTER = 0;
    int MODIFY_PASSWORD = 1;
}
