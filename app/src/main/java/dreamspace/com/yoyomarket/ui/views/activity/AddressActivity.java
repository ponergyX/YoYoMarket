package dreamspace.com.yoyomarket.ui.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kennyc.view.MultiStateView;

import javax.inject.Inject;

import butterknife.Bind;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.common.VerticalSpaceItemDecoration;
import dreamspace.com.yoyomarket.common.adapter.AddressItemAdapter;
import dreamspace.com.yoyomarket.common.base.BaseActivity;
import dreamspace.com.yoyomarket.ui.presenter.activity.AddressActivityPresenter;
import dreamspace.com.yoyomarket.ui.view.activity.AddressView;

/**
 * Created by Lx on 2016/2/3.
 */
public class AddressActivity extends BaseActivity implements AddressView{

    @Bind(R.id.address_msview)
    MultiStateView multiStateView;

    @Bind(R.id.swrlayout)
    SwipeRefreshLayout refreshLayout;

    @Bind(R.id.address_recyclerview)
    RecyclerView recyclerView;

    @Inject
    AddressActivityPresenter addressActivityPresenter;

    private AddressItemAdapter adapter;

    public static Intent getCallingIntent(Context context){
      return new Intent(context,AddressActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjector();
        initPresenter();
    }

    private void initInjector(){
        getApiComponent().inject(this);
    }

    private void initPresenter(){
        addressActivityPresenter.attchView(this);
    }

    @Override
    protected void initViewsAndEvents() {
        setTitle(getString(R.string.get_good_info));
        refreshLayout.setColorSchemeResources(R.color.app_color);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new AddressItemAdapter(this);
        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.x2));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setHasFixedSize(true);

        adapter.setOnAddressClickListener(new AddressItemAdapter.OnAddressClickListener() {
            @Override
            public void onClick() {
                navigator.navigateToModifyAddressActivity(AddressActivity.this, ModifyAddressActivity.MODIFY_ADDRESS);
            }
        });

        adapter.setOnAddAddressClickListener(new AddressItemAdapter.OnAddAddressClickListener() {
            @Override
            public void onClick() {
                navigator.navigateToModifyAddressActivity(AddressActivity.this, ModifyAddressActivity.ADD_ADDRESS);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_address;
    }
}
