package dreamspace.com.yoyomarket.ui.views.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kennyc.view.MultiStateView;
import com.orhanobut.logger.Logger;


import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.api.entity.element.MarketListItem;
import dreamspace.com.yoyomarket.common.VerticalSpaceItemDecoration;
import dreamspace.com.yoyomarket.common.adapter.MarketItemAdapter;
import dreamspace.com.yoyomarket.common.base.BaseLazyFragment;
import dreamspace.com.yoyomarket.common.qualifier.GetDataType;
import dreamspace.com.yoyomarket.common.untils.ToastUntil;
import dreamspace.com.yoyomarket.ui.presenter.fragment.MarketsFragmentPresenter;
import dreamspace.com.yoyomarket.ui.view.fragment.MarketsView;
import dreamspace.com.yoyomarket.ui.views.activity.MainActivity;
import dreamspace.com.yoyomarket.widget.LoadMoreRecyclerView;
import dreamspace.com.yoyomarket.widget.circleProgress.CircleProgress;

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

    private CircleProgress circleProgress;
    private MarketItemAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjector();
        initPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        marketsFragmentPresenter.onDestory();
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
        showLoading();
        marketsFragmentPresenter.getMarketList();
    }

    @Override
    protected void initViewsAndEvents() {
        initMutliStateView();

        if(adapter == null){
            adapter = new MarketItemAdapter(getActivity());
            adapter.setOnMarketItemClickListener(new MarketItemAdapter.OnMarketItemClickListener() {
                @Override
                public void onClick(String supId, String supName) {
                    navigator.navigateToPickGoodsActivity(getActivity(),supId,supName);
                }
            });
        }

        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(getActivity().getResources().getDimensionPixelSize(R.dimen.x2));
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);

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
                    float alpha = (float) Math.abs(topView.getTop()) / (topView.getHeight() - toolbar.getHeight());
                    if(alpha <= 1){
                        toolbar.getBackground().setAlpha((int) (alpha * 255));
                    }
                }else if(linearLayoutManager.findFirstVisibleItemPosition() == 1){
                    toolbar.getBackground().setAlpha(255);
                }
            }
        });

        recyclerView.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                marketsFragmentPresenter.getMarketList();
            }
        });

        swipeRefreshLayout.setColorSchemeResources(R.color.app_color);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                marketsFragmentPresenter.getFreshData();
            }
        });
    }

    private void initMutliStateView(){
        View errorView = multiStateView.getView(MultiStateView.VIEW_STATE_ERROR);
        Button retryBtn = (Button) errorView.findViewById(R.id.retry_btn);
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marketsFragmentPresenter.getFreshData();
            }
        });

        View loadingView = multiStateView.getView(MultiStateView.VIEW_STATE_LOADING);
        circleProgress = (CircleProgress) loadingView.findViewById(R.id.circleProgress);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_markets;
    }

    @Override
    public void showNetCantUse() {
        ToastUntil.showNetCantUse(getActivity());
    }

    @Override
    public void showToast(String s) {
        ToastUntil.showToast(s, getActivity());
    }

    @Override
    public void showNetError() {
        ToastUntil.showNetError(getActivity());
    }

    @Override
    public void showLoading() {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        if(circleProgress != null){
            circleProgress.startAnim();
        }
    }

    @Override
    public void showErrorViewState() {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        if(circleProgress != null){
            circleProgress.stopAnim();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showEmpty() {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        if(circleProgress != null){
            circleProgress.stopAnim();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showNormal(ArrayList<MarketListItem> markets, @GetDataType int type) {
        if(type == GetDataType.FIRST_GET_DATA){
            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            adapter.setData(markets);
            setGotData();
        }else{
            adapter.addData(markets);
        }

        if(circleProgress != null){
            circleProgress.stopAnim();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setLoadMoreFinish() {
        recyclerView.onLoadMoreFinish();
    }
}
