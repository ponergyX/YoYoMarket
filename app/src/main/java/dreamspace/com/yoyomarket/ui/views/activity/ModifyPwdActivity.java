package dreamspace.com.yoyomarket.ui.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.common.base.BaseActivity;
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

    @Inject
    ModifyPwdActivityPresenter modifyPwdActivityPresenter;

    private TimeCounter timeCounter;

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
    }

    @Override
    protected void initViewsAndEvents() {
        int mode = getIntent().getIntExtra(PASSWORD_MODE,MODIFY_PASSWORD);
        if(mode == MODIFY_PASSWORD){
            setTitle(getString(R.string.chang_pwd));
        }else if(mode == FIND_BACK_PASSWORD){
            setTitle(getString(R.string.find_back_pwd));
        }

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
        timeCounter = new TimeCounter(6000,1000);
        timeCounter.start();
    }

    @OnClick(R.id.confirm_btn)
    void modifyPwd(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timeCounter != null){
            timeCounter.cancel();
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_modify_pwd;
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
