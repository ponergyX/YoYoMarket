package dreamspace.com.yoyomarket.ui.views.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kennyc.view.MultiStateView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.api.entity.element.MarketListItem;
import dreamspace.com.yoyomarket.api.entity.element.OrderItem;
import dreamspace.com.yoyomarket.common.VerticalSpaceItemDecoration;
import dreamspace.com.yoyomarket.common.adapter.OrderItemAdapter;
import dreamspace.com.yoyomarket.common.base.BaseLazyFragment;
import dreamspace.com.yoyomarket.common.qualifier.GetDataType;
import dreamspace.com.yoyomarket.common.untils.ToastUntil;
import dreamspace.com.yoyomarket.ui.presenter.fragment.OrdersFragmentPresenter;
import dreamspace.com.yoyomarket.ui.view.fragment.OrdersView;
import dreamspace.com.yoyomarket.ui.views.activity.MainActivity;
import dreamspace.com.yoyomarket.widget.LoadMoreRecyclerView;
import dreamspace.com.yoyomarket.widget.circleProgress.CircleProgress;

/**
 * Created by Lx on 2016/1/28.
 */
public class OrdersFragment extends BaseLazyFragment implements OrdersView{

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.back_home)
    ImageView backHomeIv;

    @Bind(R.id.toolbar_title)
    TextView titleTv;

    @Bind(R.id.orders_msview)
    MultiStateView multiStateView;

    @Bind(R.id.swrlayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.order_recyclerview)
    LoadMoreRecyclerView recyclerView;

    @Inject
    OrdersFragmentPresenter ordersFragmentPresenter;

    private CircleProgress circleProgress;
    private OrderItemAdapter adapter;
    private SweetAlertDialog sweetAlertDialog;

    private static final int DIALOG_BEFORE_ORDER_RECEIVE = 1;
    private static final int DIALOG_AFTER_ORDER_RECEIVE = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjector();
        initPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ordersFragmentPresenter.onDestory();
    }

    private void initInjector(){
        getApiComponent().inject(this);
    }

    private void initPresenter(){
        ordersFragmentPresenter.attchView(this);
        ordersFragmentPresenter.onCreate();
    }

    @Override
    protected void lazyLoad() {
        showLoading();
        ordersFragmentPresenter.getFreshData();
    }

    @Override
    protected void initViewsAndEvents() {
        initMutliStateView();
        toolbar.setBackgroundColor(Color.parseColor("#F99C35"));
        titleTv.setText(getString(R.string.my_orders));
        backHomeIv.setVisibility(View.INVISIBLE);
        swipeRefreshLayout.setColorSchemeResources(R.color.app_color);
        if(adapter == null){

            adapter = new OrderItemAdapter(getActivity());
            adapter.setOnCancelOrderAfterMarketReceiveClickListener(new OrderItemAdapter.OnCancelOrderAfterMarketReceiveClickListener() {
                @Override
                public void onCancelClick(String orderId) {
                    initCancelDialog(DIALOG_AFTER_ORDER_RECEIVE);

                }
            });
            adapter.setOnCancelOrderBeforeMarketReceiveClickListener(new OrderItemAdapter.OnCancelOrderBeforeMarketReceiveClickListener() {
                @Override
                public void onCancelClick(String orderId) {
                    initCancelDialog(DIALOG_BEFORE_ORDER_RECEIVE);

                }
            });

            adapter.setOnGoPayClickListener(new OrderItemAdapter.OnGoPayClickListener() {
                @Override
                public void onGoPayClick(String orderId) {

                }
            });

            adapter.setOnGoCommentClickListener(new OrderItemAdapter.OnGoCommentClickListener() {
                @Override
                public void onGoCommentClick(String orderId) {

                }
            });

            adapter.setOnItemClickListener(new OrderItemAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(String orderId) {
                    navigator.navigateToOrderInfoActivity(getActivity(),orderId);
                }
            });
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(getActivity().getResources().getDimensionPixelSize(R.dimen.x17));
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                ordersFragmentPresenter.getOrderList();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ordersFragmentPresenter.getFreshData();
            }
        });
    }

    private void initMutliStateView(){
        View errorView = multiStateView.getView(MultiStateView.VIEW_STATE_ERROR);
        Button retryBtn = (Button) errorView.findViewById(R.id.retry_btn);
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                ordersFragmentPresenter.getFreshData();
            }
        });

        View loadingView = multiStateView.getView(MultiStateView.VIEW_STATE_LOADING);
        circleProgress = (CircleProgress) loadingView.findViewById(R.id.circleProgress);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_orders;
    }

    private void initCancelDialog(int type){
        sweetAlertDialog = new SweetAlertDialog(getActivity(),SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.showCancelButton(true);
        sweetAlertDialog.setTitleText("");
        sweetAlertDialog.setConfirmText("是");
        sweetAlertDialog.setCancelText("否");
        sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });
        if(type == DIALOG_BEFORE_ORDER_RECEIVE){
            sweetAlertDialog.setContentText(getString(R.string.cancel_order_before_receive_confirm));
            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {

                }
            });
        }else{
            sweetAlertDialog.setContentText(getString(R.string.cancel_order_after_receive_confirm));
            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {

                }
            });
        }

        sweetAlertDialog.show();
    }

    @Override
    public void showNetCantUse() {
        ToastUntil.showNetCantUse(getActivity());
    }

    @Override
    public void showToast(String s) {
        ToastUntil.showToast(s,getActivity());
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
    public void showNormal(ArrayList<OrderItem> markets, @GetDataType int type) {
        if(type == GetDataType.FIRST_GET_DATA){
            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            adapter.setData(markets);
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

    @Override
    public void showCancelOrderProcess() {
        if(sweetAlertDialog != null){
            sweetAlertDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
            sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#F99C35"));
            sweetAlertDialog.showCancelButton(false);
            sweetAlertDialog.setContentText(getString(R.string.in_order_cancel));
            sweetAlertDialog.setTitleText("");
        }
    }

    @Override
    public void showCancelOrderFailed() {
        if(sweetAlertDialog != null){
            sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setContentText(getString(R.string.order_cancel_failed));
            sweetAlertDialog.setTitleText("");
            sweetAlertDialog.setConfirmText("ok");
            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                }
            });
        }
    }

    @Override
    public void showCancelOrderSuccess() {
        if(sweetAlertDialog != null){
            sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            sweetAlertDialog.setContentText(getString(R.string.order_cancel_success));
            sweetAlertDialog.setTitleText("");
            sweetAlertDialog.setConfirmText("ok");
            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                    ordersFragmentPresenter.getFreshData();
                }
            });
        }
    }
}
