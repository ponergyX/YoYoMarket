package dreamspace.com.yoyomarket.ui.views.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.common.base.BaseLazyFragment;
import dreamspace.com.yoyomarket.ui.presenter.fragment.MineFragmentPresenter;
import dreamspace.com.yoyomarket.ui.view.fragment.MineView;
import dreamspace.com.yoyomarket.ui.views.activity.ModifyPwdActivity;

/**
 * Created by Lx on 2016/1/28.
 */
public class MineFragment extends BaseLazyFragment implements MineView{

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.avatar_civ)
    CircleImageView avatarCiv;

    @Bind(R.id.phone_tv)
    TextView phoneTv;

    @Bind(R.id.change_pw_tv)
    TextView changePwdTv;

    @Bind(R.id.get_good_address_rl)
    RelativeLayout addressRl;

    @Bind(R.id.feedback_rl)
    RelativeLayout feedbackRl;

    @Bind(R.id.service_rl)
    RelativeLayout serviceRl;

    @Bind(R.id.problem_rl)
    RelativeLayout problemRl;

    @Bind(R.id.about_us_rl)
    RelativeLayout aboutRl;

    @Bind(R.id.logout)
    TextView logoutTv;

    @Inject
    MineFragmentPresenter mineFragmentPresenter;

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
        mineFragmentPresenter.attchView(this);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initViewsAndEvents() {
        toolbar.setBackgroundColor(Color.parseColor("#F99C35"));
        avatarCiv.setImageDrawable(new ColorDrawable(Color.parseColor("#F99C35")));
    }

    @OnClick(R.id.logout)
    void logout(){

    }

    @OnClick(R.id.avatar_civ)
    void changeAvatar(){

    }

    @OnClick(R.id.change_pw_tv)
    void changPwd(){
        navigator.navigateToModifyPwdActivity(getActivity(), ModifyPwdActivity.MODIFY_PASSWORD);
    }

    @OnClick(R.id.get_good_address_rl)
    void checkAddressInfo(){
        navigator.navigateToAddressActivity(getActivity());
    }

    @OnClick(R.id.feedback_rl)
    void feedback(){
        navigator.navigateToFeedbackActivity(getActivity());
    }

    @OnClick(R.id.service_rl)
    void servicePhone(){

    }

    @OnClick(R.id.problem_rl)
    void checkProblem(){

    }

    @OnClick(R.id.about_us_rl)
    void checkAboutUs(){

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_mine;
    }
}
