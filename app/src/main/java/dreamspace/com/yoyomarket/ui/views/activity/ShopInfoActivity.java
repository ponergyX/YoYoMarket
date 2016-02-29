package dreamspace.com.yoyomarket.ui.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kennyc.view.MultiStateView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.api.entity.element.MarketInfo;
import dreamspace.com.yoyomarket.common.base.BaseActivity;
import dreamspace.com.yoyomarket.common.untils.ToastUntil;
import dreamspace.com.yoyomarket.ui.presenter.activity.ShopInfoActivityPresenter;
import dreamspace.com.yoyomarket.ui.view.activity.ShopInfoView;
import dreamspace.com.yoyomarket.widget.RatingBar;
import dreamspace.com.yoyomarket.widget.circleProgress.CircleProgress;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by Lx on 2016/2/6.
 */
public class ShopInfoActivity extends BaseActivity implements ShopInfoView{

    @Bind(R.id.multi_state_view)
    MultiStateView multiStateView;

    @Bind(R.id.shop_image_rl)
    RelativeLayout shopImageRl;

    @Bind(R.id.shop_image_iv)
    ImageView shopImageIv;

    @Bind(R.id.shop_name_tv)
    TextView shopNameTv;

    @Bind(R.id.market_ratingbar)
    RatingBar ratingBar;

    @Bind(R.id.send_time_tv)
    TextView sendTimeTv;

    @Bind(R.id.start_time_tv)
    TextView startTimeTv;

    @Bind(R.id.start_price_tv)
    TextView startPriceTv;

    @Bind(R.id.address_tv)
    TextView addressTv;

    @Bind(R.id.total_tv)
    TextView totalTv;

    @Bind(R.id.check_more_rl)
    RelativeLayout checkMoreRl;

    @Inject
    ShopInfoActivityPresenter shopInfoActivityPresenter;

    private CircleProgress circleProgress;

    public static Intent getCallingIntent(Context context){
        return new Intent(context,ShopInfoActivity.class);
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
        shopInfoActivityPresenter.onDestory();
    }

    private void initInjector(){
        getApiComponent().inject(this);
    }

    private void initPresenter(){
        shopInfoActivityPresenter.attchView(this);
    }

    @Override
    protected void initViewsAndEvents() {
        setTitle(getString(R.string.shop_info));
        initMultiStateView();
        ratingBar.setmClickable(false);
        ratingBar.setStar(5);
        Glide.with(this)
                .load(R.drawable.preview4)
                .bitmapTransform(new BlurTransformation(this))
                .into(shopImageIv);
    }

    private void initMultiStateView(){
        View errorView = multiStateView.getView(MultiStateView.VIEW_STATE_ERROR);
        Button retryBtn = (Button) errorView.findViewById(R.id.retry_btn);
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        View loadingView = multiStateView.getView(MultiStateView.VIEW_STATE_LOADING);
        circleProgress = (CircleProgress) loadingView.findViewById(R.id.circleProgress);
    }

    @OnClick(R.id.check_more_rl)
    void checkMoreComment(){
        navigator.navigateToAllCommentsActivity(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_shop_info;
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
    public void showErrorViewState() {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        if(circleProgress != null){
            circleProgress.stopAnim();
        }
    }

    @Override
    public void showLoading() {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        if(circleProgress != null){
            circleProgress.startAnim();
        }
    }

    @Override
    public void showNormal(MarketInfo marketInfo) {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        if(circleProgress != null){
            circleProgress.stopAnim();
        }
    }
}
