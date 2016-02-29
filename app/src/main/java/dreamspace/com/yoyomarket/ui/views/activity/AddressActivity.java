package dreamspace.com.yoyomarket.ui.views.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.kennyc.view.MultiStateView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.api.entity.element.AddressInfo;
import dreamspace.com.yoyomarket.api.entity.element.AddressesListItem;
import dreamspace.com.yoyomarket.common.VerticalSpaceItemDecoration;
import dreamspace.com.yoyomarket.common.adapter.AddressItemAdapter;
import dreamspace.com.yoyomarket.common.base.BaseActivity;
import dreamspace.com.yoyomarket.common.qualifier.GetDataType;
import dreamspace.com.yoyomarket.common.untils.ToastUntil;
import dreamspace.com.yoyomarket.ui.presenter.activity.AddressActivityPresenter;
import dreamspace.com.yoyomarket.ui.view.activity.AddressView;
import dreamspace.com.yoyomarket.widget.LoadMoreRecyclerView;
import dreamspace.com.yoyomarket.widget.circleProgress.CircleProgress;

/**
 * Created by Lx on 2016/2/3.
 */
public class AddressActivity extends BaseActivity implements AddressView{

    public static final String ADDRESS_MODE = "ADDRESS_MODE";
    public static final String ADDRESS_INFO = "ADDRESS_INFO";
    public static final String ADDRESS_ID = "ADDRESS_ID";
    public static final int PICK_ADDRESS = 1;
    public static final int CHECK_ADDRESS = 2;

    @Bind(R.id.address_msview)
    MultiStateView multiStateView;

    @Bind(R.id.swrlayout)
    SwipeRefreshLayout refreshLayout;

    @Bind(R.id.address_recyclerview)
    LoadMoreRecyclerView recyclerView;

    @Inject
    AddressActivityPresenter addressActivityPresenter;

    private CircleProgress circleProgress;
    private AddressItemAdapter adapter;
    private AlertDialog deleteDialog;
    private SweetAlertDialog sweetAlertDialog;
    private int mode;
    private static final int ADD_ADDRESS_REQUEST = 1;
    private static final int MODIFY_ADDRESS_REQUEST = 2;

    public static Intent getCallingIntent(Context context,int mode){
        Intent intent = new Intent(context,AddressActivity.class);
        intent.putExtra(ADDRESS_MODE, mode);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjector();
        initPresenter();
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        addressActivityPresenter.onDestory();
    }

    private void initInjector(){
        getApiComponent().inject(this);
    }

    private void initPresenter(){
        addressActivityPresenter.attchView(this);
        addressActivityPresenter.onCreate();
    }

    @Override
    protected void initViewsAndEvents() {
        mode = getIntent().getIntExtra(ADDRESS_MODE,CHECK_ADDRESS);
        if(mode == PICK_ADDRESS){
            setTitle(getString(R.string.pick_address));
        }else{
            setTitle(getString(R.string.get_good_info));
        }
        refreshLayout.setColorSchemeResources(R.color.app_color);
        initMultiStateView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new AddressItemAdapter(this);
        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.x2));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setHasFixedSize(true);

        if(mode == PICK_ADDRESS){
            adapter.setOnAddressClickListener(new AddressItemAdapter.OnAddressClickListener() {
                @Override
                public void onClick(String addressId, AddressInfo addressInfo) {
                    Intent intent = new Intent();
                    intent.putExtra(ADDRESS_ID,addressId);
                    intent.putExtra(ADDRESS_INFO,addressInfo);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
        }else{
            adapter.setOnAddressClickListener(new AddressItemAdapter.OnAddressClickListener() {
                @Override
                public void onClick(String addressId, AddressInfo addressInfo) {
                    navigator.navigateToModifyAddressActivity(AddressActivity.this, ModifyAddressActivity.MODIFY_ADDRESS,
                            MODIFY_ADDRESS_REQUEST, addressId, addressInfo);
                }
            });
        }

        adapter.setOnAddressLongClickListener(new AddressItemAdapter.OnAddressLongClickListener() {
            @Override
            public void onLongClick(String addressId) {
                showDeleteAddressDialog(addressId);
            }
        });

        adapter.setOnAddAddressClickListener(new AddressItemAdapter.OnAddAddressClickListener() {
            @Override
            public void onClick() {
                navigator.navigateToModifyAddressActivity(AddressActivity.this, ModifyAddressActivity.ADD_ADDRESS,
                        ADD_ADDRESS_REQUEST,null,null);
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addressActivityPresenter.getFreshData();
            }
        });

        recyclerView.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                addressActivityPresenter.getAddress();
            }
        });
    }

    private void loadData(){
        showLoading();
        addressActivityPresenter.getAddress();
    }

    private void initMultiStateView(){
        View errorView = multiStateView.getView(MultiStateView.VIEW_STATE_ERROR);
        Button retryBtn = (Button) errorView.findViewById(R.id.retry_btn);
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressActivityPresenter.getFreshData();
            }
        });

        View loadingView = multiStateView.getView(MultiStateView.VIEW_STATE_LOADING);
        circleProgress = (CircleProgress) loadingView.findViewById(R.id.circleProgress);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_address;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ADD_ADDRESS_REQUEST && resultCode == RESULT_OK){
            showLoading();
            addressActivityPresenter.getFreshData();
        }else if(requestCode == MODIFY_ADDRESS_REQUEST && resultCode == RESULT_OK){
            showLoading();
            addressActivityPresenter.getFreshData();
        }
    }

    @Override
    public void showNetCantUse() {
        ToastUntil.showNetCantUse(this);
    }

    @Override
    public void showNetError() {
        ToastUntil.showNetError(this);
    }

    @Override
    public void showErrorViewState() {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        if(circleProgress != null){
            circleProgress.stopAnim();
        }
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showToast(@NonNull String s) {
        ToastUntil.showToast(s, this);
    }

    @Override
    public void showNoAddress() {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        adapter.setData(new ArrayList<AddressesListItem>());
        if(circleProgress != null){
            circleProgress.stopAnim();
        }
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoading() {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        if(circleProgress != null){
            circleProgress.startAnim();
        }
    }

    @Override
    public void showNormal(ArrayList<AddressesListItem> addresses, @GetDataType int type) {
        if(type == GetDataType.FIRST_GET_DATA){
            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            adapter.setData(addresses);
        }else{
            adapter.addData(addresses);
        }
        if(circleProgress != null){
            circleProgress.stopAnim();
        }
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String s) {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        showToast(s);
        if(circleProgress != null){
            circleProgress.startAnim();
        }
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showDeleteAddressDialog(final String addressId) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_view_delete_address,null);
        LinearLayout deleteLl = (LinearLayout) dialogView.findViewById(R.id.deleteLl);
        deleteLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteAddressConfirmDialog(addressId);
                deleteDialog.dismiss();
            }
        });
        deleteDialog = new AlertDialog.Builder(this).setView(dialogView).create();
        deleteDialog.show();
    }

    @Override
    public void showDeleteAddressConfirmDialog(final String addressId) {
        sweetAlertDialog = new SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.showCancelButton(true);
        sweetAlertDialog.setConfirmText("删除");
        sweetAlertDialog.setCancelText("取消");
        sweetAlertDialog.setContentText(getString(R.string.confirm_delete_address));
        sweetAlertDialog.setTitleText("");
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                Logger.d("delete");
                addressActivityPresenter.deleteAddress(addressId);
            }
        });

        sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });

        sweetAlertDialog.show();
    }

    @Override
    public void showDeleteAddressProcess() {
        if(sweetAlertDialog != null){
            sweetAlertDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
            sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#F99C35"));
            sweetAlertDialog.setContentText(getString(R.string.in_delete_address));
            sweetAlertDialog.setTitleText("");
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.showCancelButton(false);
        }
    }

    @Override
    public void showDeleteError(String s) {
        if(sweetAlertDialog != null){
            sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setContentText(getString(R.string.delete_address_failed));
            sweetAlertDialog.setTitleText("");
            sweetAlertDialog.setConfirmText("ok");
            sweetAlertDialog.showCancelButton(false);
            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                }
            });
            showToast(s);
        }
    }

    @Override
    public void showDeleteSuccess() {
        if(sweetAlertDialog != null){
            sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            sweetAlertDialog.setContentText(getString(R.string.delete_address_success));
            sweetAlertDialog.setTitleText("");
            sweetAlertDialog.setConfirmText("ok");
            sweetAlertDialog.showCancelButton(false);
            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                }
            });
            addressActivityPresenter.getFreshData();
        }
    }

    @Override
    public void setLoadMoreFinish() {
        recyclerView.onLoadMoreFinish();
    }
}
