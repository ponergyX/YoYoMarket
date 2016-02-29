package dreamspace.com.yoyomarket.ui.views.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kennyc.view.MultiStateView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.api.entity.element.OrderGoodsItem;
import dreamspace.com.yoyomarket.api.entity.element.OrderInfo;
import dreamspace.com.yoyomarket.common.base.BaseActivity;
import dreamspace.com.yoyomarket.common.untils.CommonUntil;
import dreamspace.com.yoyomarket.common.untils.ToastUntil;
import dreamspace.com.yoyomarket.ui.presenter.activity.OrderInfoActivityPresenter;
import dreamspace.com.yoyomarket.ui.view.activity.OrderInfoView;
import dreamspace.com.yoyomarket.widget.circleProgress.CircleProgress;

/**
 * Created by Lx on 2016/2/26.
 */
public class OrderInfoActivity extends BaseActivity implements OrderInfoView{

    public static final String ORDER_ID = "ORDER_ID";

    @Bind(R.id.shop_iv)
    ImageView phoneIv;

    @Bind(R.id.state_iv)
    ImageView stateIv;

    @Bind(R.id.state_tv)
    TextView stateTv;

    @Bind(R.id.order_submit_iv)
    ImageView orderSubmitIv;

    @Bind(R.id.market_receive_iv)
    ImageView marketReceIv;

    @Bind(R.id.get_good_iv)
    ImageView getGoodIv;

    @Bind(R.id.comment_iv)
    ImageView commentIv;

    @Bind(R.id.cart_iv1)
    ImageView cartIv1;

    @Bind(R.id.cart_iv2)
    ImageView cartIv2;

    @Bind(R.id.cart_iv3)
    ImageView cartIv3;

    @Bind(R.id.cart_iv4)
    ImageView cartIv4;

    @Bind(R.id.operate_rl)
    RelativeLayout operateRl;

    @Bind(R.id.operate_btn)
    Button operateBtn;

    @Bind(R.id.market_ll)
    LinearLayout marketLl;

    @Bind(R.id.order_detail_ll)
    LinearLayout orderDetailLl;

    @Bind(R.id.total_tv)
    TextView totalTv;

    @Bind(R.id.order_id_tv)
    TextView orderIdTv;

    @Bind(R.id.receive_people_tv)
    TextView nameTv;

    @Bind(R.id.phone_num_tv)
    TextView phoneTv;

    @Bind(R.id.receive_address_tv)
    TextView addressTv;

    @Bind(R.id.time_tv)
    TextView timeTv;

    @Bind(R.id.deliver_time_tv)
    TextView deliverTime;

    @Bind(R.id.remark_tv)
    TextView remarkTv;

    @Bind(R.id.multi_state_view)
    MultiStateView multiStateView;

    @Inject
    OrderInfoActivityPresenter orderInfoActivityPresenter;

    private CircleProgress circleProgress;
    private OrderInfo orderInfo;
    private String orderId;

    public static Intent getCallingIntent(Context context,String orderId){
        Intent intent = new Intent(context,OrderInfoActivity.class);
        intent.putExtra(ORDER_ID,orderId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjector();
        initPresenter();
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initInjector(){
        getApiComponent().inject(this);
    }

    private void initPresenter(){
        orderInfoActivityPresenter.attchView(this);
        orderInfoActivityPresenter.onCreate();
    }

    private void loadData(){
        orderInfoActivityPresenter.getOrderInfo(orderId);
    }

    @Override
    protected void initViewsAndEvents() {
        orderId = getIntent().getStringExtra(ORDER_ID);
        initMultiStateView();
        phoneIv.setImageResource(R.drawable.order_phone);
        phoneIv.setVisibility(View.VISIBLE);
        setTitle(getString(R.string.order_info));
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

    @OnClick(R.id.shop_iv)
    void phoneMarket(){
        if(orderInfo != null){
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + orderInfo.getSup_phone_num()));
            startActivity(intent);
        }
    }

    @OnClick(R.id.operate_btn)
    void operate(){

    }

    @OnClick(R.id.market_ll)
    void inMarket(){

    }

    private void setOrderViews(){
        int index = 3;
        for(OrderGoodsItem goodsItem:orderInfo.getGoods()){
            View goodView = LayoutInflater.from(this).inflate(R.layout.view_item_order_detail,orderDetailLl,false);
            ImageView goodIv = (ImageView) goodView.findViewById(R.id.good_image_iv);
            TextView nameTv = (TextView) goodView.findViewById(R.id.good_name_tv);
            TextView numTv = (TextView) goodView.findViewById(R.id.good_num_tv);
            TextView priceTv = (TextView) goodView.findViewById(R.id.good_price_tv);
            CommonUntil.showImageInIv(this,goodsItem.getImage(),goodIv);
            nameTv.setText(goodsItem.getName());
            numTv.setText("X" + goodsItem.getQuantity());
            priceTv.setText("￥" + (double) goodsItem.getPrice() / 100);
            orderDetailLl.addView(goodView,index++);
        }
        totalTv.setText(getString(R.string.total) + " " + (double)orderInfo.getPrice() / 100);
//        orderIdTv.setText(orderInfo.get);
        nameTv.setText(orderInfo.getName());
        addressTv.setText(orderInfo.getAddress());
        phoneTv.setText(orderInfo.getPhone_num());
        timeTv.setText(orderInfo.getOrder_time());
        deliverTime.setText(orderInfo.getDeliver_time());
        remarkTv.setText(orderInfo.getRemark());

        switch (orderInfo.getStatus()){
            //退款中
            case -1:
                orderSubmitIv.setImageResource(R.drawable.order_circle);
                marketReceIv.setImageResource(R.drawable.order_circle);
                getGoodIv.setImageResource(R.drawable.order_circle);
                commentIv.setImageResource(R.drawable.order_circle);
                cartIv1.setVisibility(View.INVISIBLE);
                cartIv2.setVisibility(View.INVISIBLE);
                cartIv3.setVisibility(View.INVISIBLE);
                cartIv4.setVisibility(View.INVISIBLE);
                operateRl.setVisibility(View.GONE);
                stateIv.setVisibility(View.GONE);
                stateTv.setText(getString(R.string.in_refund));
                break;

            //已取消
            case 0:
                orderSubmitIv.setImageResource(R.drawable.order_circle);
                marketReceIv.setImageResource(R.drawable.order_circle);
                getGoodIv.setImageResource(R.drawable.order_circle);
                commentIv.setImageResource(R.drawable.order_circle);
                cartIv1.setVisibility(View.INVISIBLE);
                cartIv2.setVisibility(View.INVISIBLE);
                cartIv3.setVisibility(View.INVISIBLE);
                cartIv4.setVisibility(View.INVISIBLE);
                operateRl.setVisibility(View.GONE);
                stateIv.setVisibility(View.GONE);
                stateTv.setText(getString(R.string.already_refund));
                break;

            //待付款
            case 1:
                orderSubmitIv.setImageResource(R.drawable.order_circle);
                marketReceIv.setImageResource(R.drawable.order_circle);
                getGoodIv.setImageResource(R.drawable.order_circle);
                commentIv.setImageResource(R.drawable.order_circle);
                cartIv1.setVisibility(View.INVISIBLE);
                cartIv2.setVisibility(View.INVISIBLE);
                cartIv3.setVisibility(View.INVISIBLE);
                cartIv4.setVisibility(View.INVISIBLE);
                operateRl.setVisibility(View.VISIBLE);
                operateBtn.setText(getString(R.string.go_pay_order));
                stateIv.setVisibility(View.GONE);
                stateTv.setText(getString(R.string.not_pay_yet));
                break;

            //已付款，待接单
            case 2:
                marketReceIv.setImageResource(R.drawable.order_circle);
                getGoodIv.setImageResource(R.drawable.order_circle);
                commentIv.setImageResource(R.drawable.order_circle);
                orderSubmitIv.setVisibility(View.INVISIBLE);
                cartIv1.setVisibility(View.VISIBLE);
                cartIv2.setVisibility(View.INVISIBLE);
                cartIv3.setVisibility(View.INVISIBLE);
                cartIv4.setVisibility(View.INVISIBLE);
                operateRl.setVisibility(View.VISIBLE);
                operateBtn.setText(getString(R.string.cancel_order));
                stateIv.setVisibility(View.VISIBLE);
                stateTv.setText(getString(R.string.order_already_submit));
                break;

            //已接单，配送中
            case 3:
                orderSubmitIv.setImageResource(R.drawable.order_circle_s);
                getGoodIv.setImageResource(R.drawable.order_circle);
                commentIv.setImageResource(R.drawable.order_circle);
                marketReceIv.setVisibility(View.INVISIBLE);
                cartIv1.setVisibility(View.INVISIBLE);
                cartIv2.setVisibility(View.VISIBLE);
                cartIv3.setVisibility(View.INVISIBLE);
                cartIv4.setVisibility(View.INVISIBLE);
                operateRl.setVisibility(View.VISIBLE);
                operateBtn.setText(getString(R.string.cancel_order));
                stateIv.setVisibility(View.VISIBLE);
                stateTv.setText(getString(R.string.market_already_received_order));
                break;

            //已收货，待评价
            case 4:
                orderSubmitIv.setImageResource(R.drawable.order_circle_s);
                marketReceIv.setImageResource(R.drawable.order_circle_s);
                commentIv.setImageResource(R.drawable.order_circle);
                getGoodIv.setVisibility(View.INVISIBLE);
                cartIv1.setVisibility(View.INVISIBLE);
                cartIv2.setVisibility(View.INVISIBLE);
                cartIv3.setVisibility(View.VISIBLE);
                cartIv4.setVisibility(View.INVISIBLE);
                operateRl.setVisibility(View.VISIBLE);
                operateBtn.setText(getString(R.string.go_comment));
                stateIv.setVisibility(View.VISIBLE);
                stateTv.setText(getString(R.string.already_got_good));
                break;

            //已评价，订单完成
            case 5:
                orderSubmitIv.setImageResource(R.drawable.order_circle_s);
                marketReceIv.setImageResource(R.drawable.order_circle_s);
                getGoodIv.setImageResource(R.drawable.order_circle_s);
                commentIv.setImageResource(R.drawable.order_circle_s);
                cartIv1.setVisibility(View.INVISIBLE);
                cartIv2.setVisibility(View.INVISIBLE);
                cartIv3.setVisibility(View.INVISIBLE);
                cartIv4.setVisibility(View.INVISIBLE);
                operateRl.setVisibility(View.GONE);
                stateIv.setVisibility(View.GONE);
                stateTv.setText(getString(R.string.order_finish1));
                break;

            default:
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_order_info;
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
    public void showErrorViewState() {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        if(circleProgress != null){
            circleProgress.stopAnim();
        }
    }

    @Override
    public void showToast(@NonNull String s) {
        ToastUntil.showToast(s,this);
    }

    @Override
    public void showLoading() {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        if(circleProgress != null){
            circleProgress.startAnim();
        }
    }

    @Override
    public void showNormal(OrderInfo orderInfo) {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        if(circleProgress != null){
            circleProgress.stopAnim();
        }
        this.orderInfo = orderInfo;
        setOrderViews();
    }
}
