package dreamspace.com.yoyomarket.ui.views.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.common.base.BaseActivity;
import dreamspace.com.yoyomarket.common.untils.ToastUntil;
import dreamspace.com.yoyomarket.ui.presenter.activity.ModifyPwdActivityPresenter;
import dreamspace.com.yoyomarket.ui.view.activity.ModifyPwdView;

/**
 * Created by Lx on 2016/2/3.
 */
public class ModifyPwdActivity extends BaseActivity implements ModifyPwdView{

    public static final int MODIFY_PASSWORD = 1;
    public static final int FIND_BACK_PASSWORD = 2;
    public static final String PASSWORD_MODE = "dreamspace.com.yoyomarket.ui.views.activity.ModifyPwdActivity.PASSWORD_MODE";

    @Bind(R.id.phone_et)
    EditText phoneEt;

    @Bind(R.id.code_et)
    EditText codeEt;

    @Bind(R.id.pwd_et)
    EditText pwdEt;

    @Bind(R.id.pwd_confirm_et)
    EditText pwdConfirmEt;

    @Bind(R.id.get_code_btn)
    Button getCodeBtn;

    @Bind(R.id.confirm_btn)
    Button confirmBtn;

    @Bind(R.id.divider_phone)
    ImageView dividerPhone;

    @Bind(R.id.divider_code)
    ImageView dividerCode;

    @Bind(R.id.divider_pwd)
    ImageView dividerPwd;

    @Bind(R.id.divider_pwd_confirm)
    ImageView dividerPwdC;

    @Inject
    ModifyPwdActivityPresenter modifyPwdActivityPresenter;

    private TimeCounter timeCounter;
    private SweetAlertDialog sweetAlertDialog;

    public static Intent getCallingIntent(Context context,int mode){
        Intent intent = new Intent(context,ModifyPwdActivity.class);
        intent.putExtra(PASSWORD_MODE,mode);
        return intent;
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
        modifyPwdActivityPresenter.attchView(this);
        modifyPwdActivityPresenter.onCreate();
    }

    @Override
    protected void initViewsAndEvents() {
        int mode = getIntent().getIntExtra(PASSWORD_MODE,MODIFY_PASSWORD);
        if(mode == MODIFY_PASSWORD){
            setTitle(getString(R.string.chang_pwd));
        }else if(mode == FIND_BACK_PASSWORD){
            setTitle(getString(R.string.find_back_pwd));
        }
        dividerPhone.setImageResource(R.color.app_color);
        dividerCode.setImageResource(R.color.app_color);
        dividerPwd.setImageResource(R.color.app_color);
        dividerPwdC.setImageResource(R.color.app_color);
        getCodeBtn.setEnabled(false);
        getCodeBtn.setBackgroundResource(R.drawable.btn_gray_bg);
        phoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11) {
                    getCodeBtn.setEnabled(true);
                    getCodeBtn.setBackgroundResource(R.drawable.btn_orange_bg);
                } else {
                    getCodeBtn.setEnabled(false);
                    getCodeBtn.setBackgroundResource(R.drawable.btn_gray_bg);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.get_code_btn)
    void getCode(){
        getCodeBtn.setEnabled(false);
        getCodeBtn.setBackgroundResource(R.drawable.btn_gray_bg);
        timeCounter = new TimeCounter(60000,1000);
        timeCounter.start();
        modifyPwdActivityPresenter.getCode(phoneEt.getText().toString(),1);
    }

    @OnClick(R.id.confirm_btn)
    void modifyPwd(){
        if(phoneEt.getText().toString().length() < 11 || codeEt.getText().toString().length() < 1 ||
                pwdEt.getText().toString().length() < 1 || pwdConfirmEt.getText().length() < 1){
            showToast(getString(R.string.modify_pwd_enter_remind));
            return;
        }

        if(!pwdEt.getText().toString().equals(pwdConfirmEt.getText().toString())){
            showToast(getString(R.string.two_pwd_different1));
            return;
        }

        modifyPwdActivityPresenter.modifyPassword(phoneEt.getText().toString(),codeEt.getText().toString()
                ,pwdEt.getText().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timeCounter != null){
            timeCounter.cancel();
        }
        modifyPwdActivityPresenter.onDestory();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_modify_pwd;
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
    public void showProcessDialog() {
        sweetAlertDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#F99C35"));
        sweetAlertDialog.setContentText(getString(R.string.in_modify));
        sweetAlertDialog.setTitleText("");
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.show();
    }

    @Override
    public void showModifyError(String s) {
        if(sweetAlertDialog != null){
            sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setTitleText("");
            sweetAlertDialog.setContentText(s);
        }
    }

    @Override
    public void showModifySuccess() {
        if(sweetAlertDialog != null){
            sweetAlertDialog.setContentText(getString(R.string.modify_pwd_success));
            sweetAlertDialog.setTitleText("");
            sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            sweetAlertDialog.dismiss();
            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    ModifyPwdActivity.this.finish();
                }
            });
        }
    }

    private class TimeCounter extends CountDownTimer{

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimeCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            getCodeBtn.setText(millisUntilFinished / 1000 + "s");
        }

        @Override
        public void onFinish() {
            getCodeBtn.setText(getString(R.string.get_code));
            if(phoneEt.getText().length() == 11){
                getCodeBtn.setEnabled(true);
                getCodeBtn.setBackgroundResource(R.drawable.btn_orange_bg);
            }
        }
    }
}
