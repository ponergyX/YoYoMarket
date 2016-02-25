package dreamspace.com.yoyomarket.ui.views.fragment;

import android.graphics.Color;
import android.os.Bundle;
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
import dreamspace.com.yoyomarket.ui.presenter.fragment.NormalLoginFragmentPresenter;
import dreamspace.com.yoyomarket.ui.view.fragment.NormalLoginView;
import dreamspace.com.yoyomarket.ui.views.activity.ModifyPwdActivity;

/**
 * Created by Lx on 2016/2/2.
 */
public class NormalLoginFragment extends BaseLazyFragment implements NormalLoginView{

    @Bind(R.id.phone_et)
    EditText phoneEt;

    @Bind(R.id.pwd_et)
    EditText pwdEt;

    @Bind(R.id.login_btn)
    Button loginBtn;

    @Bind(R.id.find_pwd_tv)
    TextView findPwdTv;

    @Bind(R.id.register_tv)
    TextView registerTv;

    @Inject
    NormalLoginFragmentPresenter normalLoginFragmentPresenter;

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
        normalLoginFragmentPresenter.onDestory();
    }

    private void initInjector(){
        getApiComponent().inject(this);
    }

    private void initPresenter(){
        normalLoginFragmentPresenter.attchView(this);
        normalLoginFragmentPresenter.onCreate();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViewsAndEvents() {

    }

    @OnClick(R.id.login_btn)
    void login(){
        if(phoneEt.getText().length() < 11 || pwdEt.getText().length() < 1){
            showToast(getString(R.string.login_enter_remind));
        }else{
            normalLoginFragmentPresenter.login(phoneEt.getText().toString(),pwdEt.getText().toString());
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
        return R.layout.fragment_normal_login;
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
}
