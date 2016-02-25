package dreamspace.com.yoyomarket.ui.views.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kennyc.view.MultiStateView;

import javax.inject.Inject;

import butterknife.Bind;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.common.VerticalSpaceItemDecoration;
import dreamspace.com.yoyomarket.common.adapter.OrderItemAdapter;
import dreamspace.com.yoyomarket.common.base.BaseLazyFragment;
import dreamspace.com.yoyomarket.ui.presenter.fragment.OrdersFragmentPresenter;
import dreamspace.com.yoyomarket.ui.view.fragment.OrdersView;
import dreamspace.com.yoyomarket.ui.views.activity.MainActivity;

/**
 * Created by Lx on 2016/1/28.
 */
public class OrdersFragment extends BaseLazyFragment implements OrdersView{

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_title)
    TextView titleTv;

    @Bind(R.id.orders_msview)
    MultiStateView ordersMsv;

    @Bind(R.id.swrlayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.order_recyclerview)
    RecyclerView recyclerView;

    @Inject
    OrdersFragmentPresenter ordersFragmentPresenter;

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
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViewsAndEvents() {
        toolbar.setBackgroundColor(Color.parseColor("#F99C35"));
        titleTv.setText(getString(R.string.my_orders));
        swipeRefreshLayout.setColorSchemeResources(R.color.app_color);
        OrderItemAdapter adapter = new OrderItemAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(getActivity().getResources().getDimensionPixelSize(R.dimen.x17));
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_orders;
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
