package dreamspace.com.yoyomarket.ui.views.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.api.entity.element.AddressInfo;
import dreamspace.com.yoyomarket.common.base.BaseActivity;
import dreamspace.com.yoyomarket.common.untils.ToastUntil;
import dreamspace.com.yoyomarket.ui.presenter.activity.ModifyAddressActivityPresenter;
import dreamspace.com.yoyomarket.ui.view.activity.ModifyAddressView;

/**
 * Created by Lx on 2016/2/3.
 */
public class ModifyAddressActivity extends BaseActivity implements ModifyAddressView{

    public static final String ADDRESS_MODE = "dreamspace.com.yoyomarket.ui.views.activity.ModifyAddressActivity.ADDRESS_MODE";
    public static final String ADDRESS_ID = "ADDRESS_ID";
    public static final String ADDRESS_INFO = "ADDRESS_INFO";
    public static final int MODIFY_ADDRESS = 1;
    public static final int ADD_ADDRESS = 2;

    @Bind(R.id.location_et)
    EditText locationEt;

    @Bind(R.id.phone_et)
    EditText phoneEt;

    @Bind(R.id.name_et)
    EditText nameEt;

    @Bind(R.id.boy_iv)
    ImageView boyIv;

    @Bind(R.id.gril_iv)
    ImageView grilIv;

    @Bind(R.id.confirm_btn)
    Button confirmBtn;

    @Bind(R.id.back_home)
    ImageView backHomeIv;

    @Inject
    ModifyAddressActivityPresenter modifyAddressActivityPresenter;

    private SweetAlertDialog sweetAlertDialog;
    private boolean sex = true;//true = 男生，false = 女生
    private int mode;
    private String addressId;
    private AddressInfo addressInfo;
    private boolean modify = false;

    public static Intent getCallingIntent(Context context,int mode,@Nullable String addressId,@Nullable AddressInfo addressInfo){
        Intent intent =  new Intent(context,ModifyAddressActivity.class);
        intent.putExtra(ADDRESS_MODE, mode);
        if(addressId != null){
            intent.putExtra(ADDRESS_ID,addressId);
        }

        if(addressInfo != null){
            intent.putExtra(ADDRESS_INFO,addressInfo);
        }
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjector();
        initPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        modifyAddressActivityPresenter.onDestory();
    }

    private void initInjector(){
        getApiComponent().inject(this);
    }

    private void initPresenter(){
        modifyAddressActivityPresenter.attchView(this);
    }

    @Override
    protected void initViewsAndEvents() {
        mode = getIntent().getIntExtra(ADDRESS_MODE,MODIFY_ADDRESS);
        if(mode == MODIFY_ADDRESS){
            setTitle(getString(R.string.modify_address));
            addressId = getIntent().getStringExtra(ADDRESS_ID);
            addressInfo = getIntent().getParcelableExtra(ADDRESS_INFO);
            phoneEt.setText(addressInfo.getPhone_num());
            nameEt.setText(addressInfo.getName());
            locationEt.setText(addressInfo.getAddress());
            if(addressInfo.getSex().equals("女生")){
                grilClick();
            }
        }else{
            setTitle(getString(R.string.add_address));
        }
        backHomeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(modify){
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }

    @OnClick(R.id.boy_iv)
    void boyClick(){
        if(!sex){
            sex = true;
            boyIv.setImageResource(R.drawable.ic_sex_checkbox_s);
            grilIv.setImageResource(R.drawable.ic_sex_checkbox);
        }
    }

    @OnClick(R.id.gril_iv)
    void grilClick(){
        if(sex){
            sex = false;
            boyIv.setImageResource(R.drawable.ic_sex_checkbox);
            grilIv.setImageResource(R.drawable.ic_sex_checkbox_s);
        }
    }

    @OnClick(R.id.confirm_btn)
    void confirm(){
        if(locationEt.getText().toString().length() < 1 || phoneEt.getText().toString().length() < 11 ||
                nameEt.getText().toString().length() < 1){
            showToast(getString(R.string.address_remind));
            return;
        }

        String sex1 = sex? "男生":"女生";
        if(mode == ADD_ADDRESS){
            modifyAddressActivityPresenter.createAddress(phoneEt.getText().toString(),locationEt.getText().toString(),
                    sex1,nameEt.getText().toString());
        }else{
            modifyAddressActivityPresenter.modifyAddress(phoneEt.getText().toString(), locationEt.getText().toString(),
                    sex1, nameEt.getText().toString(), addressId);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_modify_address;
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
    public void showToast(@NonNull String s) {
        ToastUntil.showToast(s,this);
    }

    @Override
    public void showCreateProcess() {
        sweetAlertDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#F99C35"));
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setContentText(getString(R.string.in_create_address));
        sweetAlertDialog.setTitleText("");
        sweetAlertDialog.show();
    }

    @Override
    public void showModifyProcess() {
        sweetAlertDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#F99C35"));
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setContentText(getString(R.string.in_modify_address));
        sweetAlertDialog.setTitleText("");
        sweetAlertDialog.show();
    }

    @Override
    public void showCreateError(String s) {
        if(sweetAlertDialog != null){
            sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setContentText(getString(R.string.create_address_fail));
            sweetAlertDialog.setTitleText("");
            showToast(s);
        }
    }

    @Override
    public void showModifyError(String s) {
        if(sweetAlertDialog != null){
            sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setContentText(getString(R.string.modify_address_fail));
            sweetAlertDialog.setTitleText("");
            showToast(s);
        }
    }

    @Override
    public void showCreateSuccess() {
        if(sweetAlertDialog != null){
            sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            sweetAlertDialog.setContentText(getString(R.string.create_address_success));
            sweetAlertDialog.setTitleText("");
            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    setResult(RESULT_OK);
                    finish();
                }
            });
        }
    }

    @Override
    public void showModifySuccess() {
        if(sweetAlertDialog != null){
            sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            sweetAlertDialog.setContentText(getString(R.string.modify_address_success));
            sweetAlertDialog.setTitleText("");
            modify = true;
        }
    }

    @Override
    public void onBackPressed() {
        if(modify){
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }
}
