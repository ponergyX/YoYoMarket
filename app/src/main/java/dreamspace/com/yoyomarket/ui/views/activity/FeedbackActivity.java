package dreamspace.com.yoyomarket.ui.views.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.api.entity.element.SuggestionReq;
import dreamspace.com.yoyomarket.common.base.BaseActivity;
import dreamspace.com.yoyomarket.common.untils.ToastUntil;
import dreamspace.com.yoyomarket.ui.presenter.activity.FeedbackActivityPresenter;
import dreamspace.com.yoyomarket.ui.view.activity.FeedbackView;

/**
 * Created by Lx on 2016/2/4.
 */
public class FeedbackActivity extends BaseActivity implements FeedbackView{

    @Bind(R.id.feedback_info_et)
    EditText feedbackEt;

    @Bind(R.id.contact_way_et)
    EditText contactEt;

    @Bind(R.id.submit_btn)
    Button submitBtn;

    @Inject
    FeedbackActivityPresenter feedbackActivityPresenter;
    private SweetAlertDialog sweetAlertDialog;

    public static Intent getCallingIntent(Context context){
        return new Intent(context,FeedbackActivity.class);
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
        feedbackActivityPresenter.onDestory();
    }

    private void initInjector(){
        getApiComponent().inject(this);
    }

    private void initPresenter(){
        feedbackActivityPresenter.attchView(this);
    }

    @Override
    protected void initViewsAndEvents() {
        setTitle(getString(R.string.feedback));
    }

    @OnClick(R.id.submit_btn)
    void submit(){
        if(isValiad()){
            SuggestionReq suggestionReq = new SuggestionReq();
            suggestionReq.setContent(feedbackEt.getText().toString().trim());
            suggestionReq.setContact_info(contactEt.getText().toString().trim());
            feedbackActivityPresenter.feedback(suggestionReq);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_feedback;
    }

    private boolean isValiad(){
        if(feedbackEt.getText().toString().trim().length() < 6){
            showToast(getString(R.string.feedback_content_length_remind));
            return false;
        }

        if(contactEt.getText().toString().trim().length() == 0){
            showToast(getString(R.string.plz_enter_contact_info));
            return false;
        }

        return true;
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
    public void showSuggestError(String s) {
        if(sweetAlertDialog != null){
            sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setContentText(getString(R.string.submit_error));
            sweetAlertDialog.setTitleText("");
            showToast(s);
        }
    }

    @Override
    public void showSuggestSuccess() {
        if(sweetAlertDialog != null){
            sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            sweetAlertDialog.setContentText(getString(R.string.submit_success));
            sweetAlertDialog.setTitleText("");
            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    finish();
                }
            });
        }
    }

    @Override
    public void showSuggestProcess() {
        sweetAlertDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#F99C35"));
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setTitleText("");
        sweetAlertDialog.setContentText(getString(R.string.feedback_submitting));
        sweetAlertDialog.show();
    }
}
