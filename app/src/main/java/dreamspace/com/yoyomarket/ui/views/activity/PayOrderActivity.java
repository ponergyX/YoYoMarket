package dreamspace.com.yoyomarket.ui.views.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.common.base.BaseActivity;
import dreamspace.com.yoyomarket.ui.presenter.activity.PayOrderActivityPresenter;
import dreamspace.com.yoyomarket.ui.view.activity.PayOrderView;

/**
 * Created by Lx on 2016/2/24.
 */
public class PayOrderActivity extends BaseActivity implements PayOrderView{

    @Bind(R.id.order_pay_remain_tv)
    TextView payRemainTv;

    @Bind(R.id.money_tv)
    TextView moneyTv;

    @Bind(R.id.go_pay_btn)
    TextView goPayBtn;

    @Bind(R.id.pick_address_rl)
    RelativeLayout pickAddressRl;

    @Bind(R.id.avatar_civ)
    CircleImageView avatarCiv;

    @Bind(R.id.name_tv)
    TextView nameTv;

    @Bind(R.id.phone_tv)
    TextView phoneTv;

    @Bind(R.id.address_tv)
    TextView addressTv;

    @Bind(R.id.wechat_iv)
    ImageView wechatIv;

    @Bind(R.id.alipay_iv)
    ImageView alipayIv;

    @Bind(R.id.deliver_time_ll)
    LinearLayout deliverLl;

    @Bind(R.id.remark_et)
    EditText remarkEt;

    @Bind(R.id.total_tv)
    TextView totalTv;

    @Bind(R.id.order_detail_ll)
    LinearLayout detailLl;

    @Inject
    PayOrderActivityPresenter payOrderActivityPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjector();
        initPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        payOrderActivityPresenter.onDestory();
    }

    private void initInjector(){
        getApiComponent().inject(this);
    }

    private void initPresenter(){
        payOrderActivityPresenter.attchView(this);
        payOrderActivityPresenter.onCreate();
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @OnClick(R.id.pick_address_rl)
    void pickAddress(){

    }

    @OnClick(R.id.wechat_iv)
    void selectWechat(){

    }

    @OnClick(R.id.alipay_iv)
    void selectAlipay(){

    }

    @OnClick(R.id.go_pay_btn)
    void goPay(){

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_pay_order;
    }
}
