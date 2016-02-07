package dreamspace.com.yoyomarket.ui.views.fragment;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.common.base.BaseLazyFragment;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjector();
        initPresenter();
    }

    private void initInjector(){
        getApiComponent().inject(this);
    }

    private void initPresenter(){
        normalLoginFragmentPresenter.attchView(this);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViewsAndEvents() {

    }

    @OnClick(R.id.login_btn)
    void login(){
        navigator.navigateToMainActivity(getActivity());
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
}
