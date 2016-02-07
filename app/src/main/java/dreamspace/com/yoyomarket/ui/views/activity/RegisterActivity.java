package dreamspace.com.yoyomarket.ui.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.common.base.BaseActivity;
import dreamspace.com.yoyomarket.ui.presenter.activity.RegisterActivityPresenter;
import dreamspace.com.yoyomarket.ui.view.activity.RegisterView;

/**
 * Created by Lx on 2016/2/2.
 */
public class RegisterActivity extends BaseActivity implements RegisterView{

    @Bind(R.id.input_phone_tv)
    TextView inputPhoneTv;

    @Bind(R.id.input_code_tv)
    TextView inputCodeTV;

    @Bind(R.id.input_pwd_tv)
    TextView inputPwdTv;

    @Bind(R.id.arrow_tv1)
    TextView arrowTv1;

    @Bind(R.id.arrow_tv2)
    TextView arrowTv2;

    @Bind(R.id.register_view1)
    RelativeLayout registerView1;

    @Bind(R.id.register_viewstub2)
    ViewStub registerViewStub2;

    @Bind(R.id.register_viewstub3)
    ViewStub registerViewStub3;

    @Bind(R.id.phone_et)
    EditText phoneEt;

    @Bind(R.id.get_code_btn)
    Button getCodeBtn;

    @Bind(R.id.checkbox_iv)
    ImageView checkBoxIv;

    @Bind(R.id.user_protocol_tv)
    TextView protocolTv;

    @Inject
    RegisterActivityPresenter registerActivityPresenter;

    private boolean acceptProtocol = true;
    private boolean getCode = false;
    private View registerView2;
    private View registerView3;
    private RegisterView2 rView2;
    private RegisterView3 rView3;
    private TimeCounter timeCounter;

    public static Intent getCallingIntent(Context context){
        return new Intent(context,RegisterActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjector();
        initPresenter();
    }

    @Override
    protected void initViewsAndEvents() {
        setTitle(getString(R.string.register));
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

    private void initInjector(){
        getApiComponent().inject(this);
    }

    private void initPresenter(){
        registerActivityPresenter.attchView(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_register;
    }

    @OnClick(R.id.get_code_btn)
    void getCode(){
        getCode = true;
        checkBoxIv.setEnabled(false);
        getCodeBtn.setEnabled(false);
        getCodeBtn.setBackgroundResource(R.drawable.btn_gray_bg);
        timeCounter = new TimeCounter(60000,1000);
        timeCounter.start();

        registerView1.setVisibility(View.GONE);
        if(registerView2 == null){
            registerView2 = registerViewStub2.inflate();
        }
        inputCodeTV.setTextColor(getResources().getColor(R.color.black1));
        arrowTv2.setTextColor(getResources().getColor(R.color.black1));
        rView2 = new RegisterView2(registerView2);
    }

    @OnClick(R.id.checkbox_iv)
    void acceptProtocol(){
        if(acceptProtocol){
            acceptProtocol = false;
            getCodeBtn.setEnabled(false);
            getCodeBtn.setBackgroundResource(R.drawable.btn_gray_bg);
            checkBoxIv.setImageResource(R.drawable.ic_checkbox);
        }else{
            acceptProtocol = true;
            checkBoxIv.setImageResource(R.drawable.ic_checkbox_select);
            if(phoneEt.getText().length() == 11){
                getCodeBtn.setEnabled(true);
                getCodeBtn.setBackgroundResource(R.drawable.btn_orange_bg);
            }
        }
    }

    @OnClick(R.id.user_protocol_tv)
    void readProtocol(){

    }

    @OnClick(R.id.input_phone_tv)
    void backGetCode(){
        if(getCode){
            getCode = false;
            registerView2.setVisibility(View.GONE);
            registerView1.setVisibility(View.VISIBLE);
            inputCodeTV.setTextColor(getResources().getColor(R.color.gray4));
            arrowTv2.setTextColor(getResources().getColor(R.color.gray4));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timeCounter != null){
            timeCounter.cancel();
        }
    }

    public class RegisterView2{
        @Bind(R.id.submit_code_et)
        EditText submitCodeEt;

        @Bind(R.id.submit_code_btn)
        Button submitCodeBtn;

        public RegisterView2(View view){
            ButterKnife.bind(this,view);
        }

        @OnClick(R.id.submit_code_btn)
        void subCode(){
            registerView2.setVisibility(View.GONE);
            registerView3 = registerViewStub3.inflate();
            inputPwdTv.setTextColor(getResources().getColor(R.color.black1));
            rView3 = new RegisterView3(registerView3);
        }
    }

    public class RegisterView3{
        @Bind(R.id.pwd_et)
        EditText pwdEt;

        @Bind(R.id.pwd_confrim_et)
        EditText pwdConfirmEt;

        @Bind(R.id.different_pwd_tv)
        TextView differentPwdTv;

        @Bind(R.id.confirm_btn)
        Button confirmBtn;

        public RegisterView3(View v){
            ButterKnife.bind(this,v);
        }

        @OnClick(R.id.confirm_btn)
        void confirm(){

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
