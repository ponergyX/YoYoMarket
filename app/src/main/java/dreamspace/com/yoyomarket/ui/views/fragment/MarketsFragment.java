package dreamspace.com.yoyomarket.ui.views.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kennyc.view.MultiStateView;
import com.orhanobut.logger.Logger;


import javax.inject.Inject;

import butterknife.Bind;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.common.VerticalSpaceItemDecoration;
import dreamspace.com.yoyomarket.common.adapter.MarketItemAdapter;
import dreamspace.com.yoyomarket.common.base.BaseLazyFragment;
import dreamspace.com.yoyomarket.ui.presenter.fragment.MarketsFragmentPresenter;
import dreamspace.com.yoyomarket.ui.view.fragment.MarketsView;
import dreamspace.com.yoyomarket.ui.views.activity.MainActivity;
import dreamspace.com.yoyomarket.widget.LoadMoreRecyclerView;

/**
 * Created by Lx on 2016/1/28.
 */
public class MarketsFragment extends BaseLazyFragment implements MarketsView{
    @Bind(R.id.market_multi_state_view)
    MultiStateView multiStateView;

    @Bind(R.id.market_recyclerview)
    LoadMoreRecyclerView recyclerView;

    @Bind(R.id.location_tv)
    TextView locationTv;

    @Bind(R.id.location_toolbar)
    Toolbar toolbar;

    @Bind(R.id.swrlayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    MarketsFragmentPresenter marketsFragmentPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjector();
        initPresenter();
    }

    private void initPresenter(){
        marketsFragmentPresenter.attchView(this);
        marketsFragmentPresenter.onCreate();
    }

    private void initInjector(){
        getApiComponent().inject(this);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViewsAndEvents() {
        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(getActivity().getResources().getDimensionPixelSize(R.dimen.x2));
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        MarketItemAdapter adapter = new MarketItemAdapter(getActivity());

        adapter.setOnMarketItemClickListener(new MarketItemAdapter.OnMarketItemClickListener() {
            @Override
            public void onClick() {
                navigator.navigateToPickGoodsActivity(getActivity());
            }
        });

        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (linearLayoutManager.findFirstVisibleItemPosition() == 0) {
                    View topView = recyclerView.getChildAt(0);
                    float alpha = (float) Math.abs(topView.getTop()) / topView.getHeight();
                    toolbar.getBackground().setAlpha((int) (alpha * 255));
                }else if(linearLayoutManager.findFirstVisibleItemPosition() == 1){
                    toolbar.getBackground().setAlpha(255);
                }
            }
        });

        recyclerView.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                recyclerView.onLoadMoreFinish();
            }
        });

        swipeRefreshLayout.setColorSchemeResources(R.color.app_color);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_markets;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showNormal() {

    }
}
