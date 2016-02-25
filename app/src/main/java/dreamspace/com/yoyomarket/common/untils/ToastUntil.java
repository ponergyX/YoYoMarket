package dreamspace.com.yoyomarket.common.untils;

import android.content.Context;
import android.widget.Toast;

import dreamspace.com.yoyomarket.R;

/**
 * Created by Lx on 2016/2/15.
 */
public class ToastUntil {
    public static void showToast(String s,Context context){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }

    public static void showNetCantUse(Context context){
        Toast.makeText(context,context.getString(R.string.net_cant_use),Toast.LENGTH_SHORT).show();
    }

    public static void showNetError(Context context){
        Toast.makeText(context,context.getString(R.string.net_error),Toast.LENGTH_SHORT).show();
    }
}
