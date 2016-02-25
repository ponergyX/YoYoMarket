package dreamspace.com.yoyomarket.ui.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.common.base.BaseActivity;
import dreamspace.com.yoyomarket.ui.presenter.activity.ShopInfoActivityPresenter;
import dreamspace.com.yoyomarket.ui.view.activity.ShopInfoView;
import dreamspace.com.yoyomarket.widget.RatingBar;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by Lx on 2016/2/6.
 */
public class ShopInfoActivity extends BaseActivity implements ShopInfoView{

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
        ratingBar.setmClickable(false);
        ratingBar.setStar(5);
        Glide.with(this)
                .load(R.drawable.preview4)
                .bitmapTransform(new BlurTransformation(this))
                .into(shopImageIv);
    }

    @OnClick(R.id.check_more_rl)
    void checkMoreComment(){
        navigator.navigaeToAllCommentsActivity(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_shop_info;
    }
}
