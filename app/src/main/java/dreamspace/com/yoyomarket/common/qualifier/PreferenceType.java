package dreamspace.com.yoyomarket.common.qualifier;


import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Lx on 2016/2/15.
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({
        PreferenceType.PHONE,
        PreferenceType.ACCESS_TOKEN,
        PreferenceType.AVATAR
})
public @interface PreferenceType {
    String PHONE = "dreamspace.com.yoyomarket.phone";
    String ACCESS_TOKEN = "dreamspace.com.yoyomarket.access";
    String AVATAR = "dreamspace.com.yoyomarket.avatar";
}
