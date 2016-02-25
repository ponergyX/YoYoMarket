package dreamspace.com.yoyomarket.common.untils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lx on 2016/2/21.
 */
public class CommonUntil {
    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static void showImageInCiv(Context context,String path,CircleImageView circleImageView){
        Glide.with(context).load(path)
                .centerCrop()
                .into(circleImageView);
    }

    public static void showImageInIv(Context context,String path,ImageView imageView){
        Glide.with(context).load(path)
                .centerCrop()
                .into(imageView);
    }
}
