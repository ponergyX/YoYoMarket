package dreamspace.com.yoyomarket.ui.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.common.base.BaseActivity;
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

    public static Intent getCallingIntent(Context context){
        return new Intent(context,FeedbackActivity.class);
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
        feedbackActivityPresenter.attchView(this);
    }

    @Override
    protected void initViewsAndEvents() {
        setTitle(getString(R.string.feedback));
    }

    @OnClick(R.id.submit_btn)
    void submit(){

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_feedback;
    }
}
