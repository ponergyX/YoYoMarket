package dreamspace.com.yoyomarket.ui.views.fragment;

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
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.common.base.BaseLazyFragment;
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
        phoneLoginFragmentPresenter.attchView(this);
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
                if(s.length() == 11){
                    getCodeBtn.setEnabled(true);
                    getCodeBtn.setBackgroundResource(R.drawable.btn_orange_bg);
                }else{
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
        return R.layout.fragment_phone_login;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(timeCounter != null){
            timeCounter.cancel();
        }
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
