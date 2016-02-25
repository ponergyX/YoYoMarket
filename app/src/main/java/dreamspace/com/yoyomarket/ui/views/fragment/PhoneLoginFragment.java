package dreamspace.com.yoyomarket.ui.views.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.common.base.BaseLazyFragment;
import dreamspace.com.yoyomarket.common.untils.ToastUntil;
import dreamspace.com.yoyomarket.ui.presenter.fragment.PhoneLoginFragmentPresenter;
import dreamspace.com.yoyomarket.ui.view.fragment.PhoneLoginView;
import dreamspace.com.yoyomarket.ui.views.activity.ModifyPwdActivity;

/**
 * Created by Lx on 2016/2/2.
 */
public class PhoneLoginFragment extends BaseLazyFragment implements PhoneLoginView{

    @Bind(R.id.phone_et)
    EditText phoneEt;

    @Bind(R.id.get_code_btn)
    Button getCodeBtn;

    @Bind(R.id.code_et)
    EditText codeEt;

    @Bind(R.id.login_btn)
    Button loginBtn;

    @Bind(R.id.find_pwd_tv)
    TextView findPwdTv;

    @Bind(R.id.register_tv)
    TextView registerTv;

    @Inject
    PhoneLoginFragmentPresenter phoneLoginFragmentPresenter;

    private TimeCounter timeCounter;
    private SweetAlertDialog sweetAlertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjector();
        initPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        phoneLoginFragmentPresenter.onDestory();
    }

    private void initInjector(){
        getApiComponent().inject(this);
    }

    private void initPresenter(){
        phoneLoginFragmentPresenter.attchView(this);
        phoneLoginFragmentPresenter.onCreate();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViewsAndEvents() {
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
        timeCounter = new TimeCounter(60000,1000);
        getCodeBtn.setEnabled(false);
        getCodeBtn.setBackgroundResource(R.drawable.btn_gray_bg);
        timeCounter.start();
        phoneLoginFragmentPresenter.getCode(phoneEt.getText().toString(),3);
    }

    @OnClick(R.id.login_btn)
    void login(){
        if(phoneEt.getText().toString().length() < 11 || codeEt.getText().length() < 1){
            ToastUntil.showToast(getString(R.string.code_login_enter_remind),getActivity());
        }else{
            phoneLoginFragmentPresenter.login(phoneEt.getText().toString(),codeEt.getText().toString());
        }
    }

    @OnClick(R.id.find_pwd_tv)
    void findPwd(){
        navigator.navigateToModifyPwdActivity(getActivity(), ModifyPwdActivity.FIND_BACK_PASSWORD);
    }

    @OnClick(R.id.register_tv)
    void register(){
        navigator.navigateToRegisterActivity(getActivity());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_phone_login;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(timeCounter != null){
            timeCounter.cancel();
        }
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
    public void showLoginProcessDialog() {
        sweetAlertDialog = new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#F99C35"));
        sweetAlertDialog.setContentText(getString(R.string.in_login));
        sweetAlertDialog.setTitleText("");
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.show();
    }

    @Override
    public void hideLoginProcessDialog() {
        if(sweetAlertDialog != null){
            sweetAlertDialog.dismiss();
        }
    }

    @Override
    public void loginError(String s) {
        if(sweetAlertDialog != null){
            sweetAlertDialog.setContentText(s);
            sweetAlertDialog.setTitleText("");
            sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
        }
    }

    @Override
    public void loginSuccess() {
        if(sweetAlertDialog != null){
            sweetAlertDialog.setContentText(getString(R.string.login_success));
            sweetAlertDialog.setTitleText("");
            sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            sweetAlertDialog.dismiss();
            navigator.navigateToMainActivity(getActivity());
        }
    }

    @Override
    public void setTextInPhoneEt(String s) {
        phoneEt.setText(s);
    }

    public class TimeCounter extends CountDownTimer{

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
            getCodeBtn.setText(millisUntilFinished / 1000 + " s");
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
