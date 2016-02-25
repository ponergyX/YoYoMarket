package dreamspace.com.yoyomarket.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.orhanobut.logger.Logger;

/**
 * Created by Lx on 2016/1/31.
 */
public class LoadMoreRecyclerView extends RecyclerView{
    private OnScrollListener mOnScrollListener;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoadingMore;

    public LoadMoreRecyclerView(Context context) {
        super(context);
        init();
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void addOnScrollListener(RecyclerView.OnScrollListener onScrollListener){
        mOnScrollListener = onScrollListener;
    }

    private void init(){
        super.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mOnScrollListener != null) {
                    mOnScrollListener.onScrolled(recyclerView, dx, dy);
                }

                if (getLayoutManager() != null && getLayoutManager() instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();
                    int reduce = linearLayoutManager.getItemCount() - linearLayoutManager.findLastVisibleItemPosition();
                    if((linearLayoutManager.findLastVisibleItemPosition() - linearLayoutManager.findFirstVisibleItemPosition() + 1) == linearLayoutManager.getItemCount()){
                        return;
                    }

                    if (reduce <= 1) {
                        if (onLoadMoreListener != null && !isLoadingMore) {
                            isLoadingMore = true;
                            onLoadMoreListener.onLoadMore();
                        }
                    }
                }
            }
        });
    }

    public void onLoadMoreFinish(){
        isLoadingMore = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener{
        void onLoadMore();
    }
}
