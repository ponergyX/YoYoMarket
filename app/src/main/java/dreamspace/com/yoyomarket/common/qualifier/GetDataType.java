package dreamspace.com.yoyomarket.common.qualifier;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Lx on 2016/2/22.
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({
        GetDataType.FIRST_GET_DATA,
        GetDataType.LOAD_MORE
})

public @interface GetDataType{
    int FIRST_GET_DATA = 0;
    int LOAD_MORE = 1;
}