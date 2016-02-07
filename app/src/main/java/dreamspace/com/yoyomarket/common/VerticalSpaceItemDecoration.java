package dreamspace.com.yoyomarket.common;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Lx on 2016/2/2.
 */
public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration{

    private final int spaceHeight;

    public VerticalSpaceItemDecoration(int spaceHeight){
        this.spaceHeight = spaceHeight;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            outRect.bottom = spaceHeight;
        }
    }
}
