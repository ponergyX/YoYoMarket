package dreamspace.com.yoyomarket.ui.views.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kennyc.view.MultiStateView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.logger.Logger;
import com.pingplusplus.android.PaymentActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.api.entity.element.AddressInfo;
import dreamspace.com.yoyomarket.api.entity.element.BuyGoodItem;
import dreamspace.com.yoyomarket.api.entity.element.CreateOrderInfo;
import dreamspace.com.yoyomarket.api.entity.element.DeliverTime;
import dreamspace.com.yoyomarket.api.entity.element.GoodInfo;
import dreamspace.com.yoyomarket.api.entity.element.OrderChannel;
import dreamspace.com.yoyomarket.common.adapter.PickTimeItemAdapter;
import dreamspace.com.yoyomarket.common.base.BaseActivity;
import dreamspace.com.yoyomarket.common.untils.CommonUntil;
import dreamspace.com.yoyomarket.common.untils.ToastUntil;
import dreamspace.com.yoyomarket.ui.presenter.activity.PayOrderActivityPresenter;
import dreamspace.com.yoyomarket.ui.view.activity.PayOrderView;
import dreamspace.com.yoyomarket.widget.circleProgress.CircleProgress;

/**
 * Created by Lx on 2016/2/24.
 */
public class PayOrderActivity extends BaseActivity implements PayOrderView{

    public static final String SUP_ID = "SUP_ID";
    public static final String PICK_GOODS = "PICK_GOODS";

    @Bind(R.id.multi_state_view)
    MultiStateView multiStateView;

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

    @Bind(R.id.time_tv)
    TextView timeTv;

    @Bind(R.id.remark_et)
    EditText remarkEt;

    @Bind(R.id.total_tv)
    TextView totalTv;

    @Bind(R.id.order_detail_ll)
    LinearLayout detailLl;

    @Bind(R.id.pick_remind_rl)
    RelativeLayout pickAddressRemindRl;

    @Bind(R.id.address_info_rl)
    RelativeLayout addressInfoRl;

    @Inject
    PayOrderActivityPresenter payOrderActivityPresenter;

    private CircleProgress circleProgress;
    private SweetAlertDialog sweetAlertDialog;
    private DialogPlus pickTimeDialog;
    private String supId;
    private HashMap<String,GoodInfo> pickGoods;
    private ArrayList<BuyGoodItem> buyGoods;
    private boolean pickAddress = false;
    private String addressId;
    private int payWay = WECHAT;
    private int totalMoney = 0;

    private static final int WECHAT = 1;
    private static final int ALIPAY = 2;
    private static final int PICK_ADDRESS = 1;
    private static final int REQUEST_CODE_PAYMENT = 2;

    public static Intent getCallingIntent(@NonNull Context context,String supId,HashMap<String,GoodInfo> pickGoods){
        Intent intent = new Intent(context,PayOrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(SUP_ID, supId);
        bundle.putSerializable(PICK_GOODS, pickGoods);
        intent.putExtras(bundle);
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
        setTitle(getString(R.string.submit_order));
        Bundle bundle = getIntent().getExtras();
        supId = bundle.getString(SUP_ID);
        pickGoods = (HashMap<String, GoodInfo>) bundle.getSerializable(PICK_GOODS);

        initMultiStateView();
        addPickGoodsDetailViews();
    }

    private void initMultiStateView(){
        View errorView = multiStateView.getView(MultiStateView.VIEW_STATE_ERROR);
        Button retryBtn = (Button) errorView.findViewById(R.id.retry_btn);
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payOrderActivityPresenter.getDeliverTimes();
            }
        });

        View loadingView = multiStateView.getView(MultiStateView.VIEW_STATE_LOADING);
        circleProgress = (CircleProgress) loadingView.findViewById(R.id.circleProgress);
    }

    private void loadData(){
        payOrderActivityPresenter.getDeliverTimes();
    }

    @OnClick(R.id.pick_address_rl)
    void pickAddress(){
        navigator.navigateToAddressActivity(this, PICK_ADDRESS, AddressActivity.PICK_ADDRESS);
    }

    @OnClick(R.id.wechat_iv)
    void selectWechat(){
        if(payWay != WECHAT){
            payWay = WECHAT;
            wechatIv.setBackgroundResource(R.drawable.pay_way_bg_s);
            alipayIv.setBackgroundResource(R.drawable.pay_way_bg);
        }
    }

    @OnClick(R.id.alipay_iv)
    void selectAlipay(){
        if(payWay != ALIPAY){
            payWay = ALIPAY;
            wechatIv.setBackgroundResource(R.drawable.pay_way_bg);
            alipayIv.setBackgroundResource(R.drawable.pay_way_bg_s);
        }
    }

    @OnClick(R.id.go_pay_btn)
    void goPay(){
        if(isValiad()){
            CreateOrderInfo info = new CreateOrderInfo();
            info.setAddress_id(addressId);
            info.setDeliver_time(timeTv.getText().toString());
            info.setGoods(buyGoods);
            info.setPrice(totalMoney);
            info.setSup_id(supId);
            String remark = remarkEt.getText().toString().trim();
            if(remark.length() > 0){
                info.setRemark(remark);
            }

            OrderChannel orderChannel = new OrderChannel();
            if(payWay == WECHAT){
                orderChannel.setChannel(getString(R.string.channel_wx));
            }else if(payWay == ALIPAY){
                orderChannel.setChannel(getString(R.string.channel_alipay));
            }
            payOrderActivityPresenter.createOrder(info,orderChannel);
        }
    }

    @OnClick(R.id.deliver_time_ll)
    void pickTime(){
        if(pickTimeDialog != null){
            pickTimeDialog.show();
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_pay_order;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_ADDRESS && resultCode == RESULT_OK){
            addressId = data.getStringExtra(AddressActivity.ADDRESS_ID);
            AddressInfo addressInfo = data.getParcelableExtra(AddressActivity.ADDRESS_INFO);
            pickAddressRemindRl.setVisibility(View.GONE);
            addressInfoRl.setVisibility(View.VISIBLE);
            nameTv.setText(addressInfo.getName());
            phoneTv.setText(addressInfo.getPhone_num());
            addressTv.setText(addressInfo.getAddress());
            pickAddress = true;
        }

        //支付页面返回处理
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                /* 处理返回值
                 * "success" - 支付成功
                 * "fail"    - 支付失败
                 * "cancel"  - 取消支付
                 * "invalid" - 支付插件未安装（一般是微信客户端未安装的情况）
                 */

                if(result.equals("success")){
                    Logger.d("success");
                }else if(result.equals("fail")){
                    Logger.d("fail");
                    String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                    String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
                    Logger.d(errorMsg);
                    Logger.d(extraMsg);
                }else if(result.equals("cancel")){
                    Logger.d("cancel");
                }else if(result.equals("invalid")){
                    Logger.d("invalid");
                }
            }
        }
    }

    private void addPickGoodsDetailViews(){
        buyGoods = new ArrayList<>();
        Iterator<GoodInfo> iterator = pickGoods.values().iterator();
        int index = 1;
        while ((iterator.hasNext())){
            GoodInfo goodInfo = iterator.next();
            View goodView = getLayoutInflater().inflate(R.layout.view_item_order_detail, detailLl, false);
            ImageView goodImageIv = (ImageView) goodView.findViewById(R.id.good_image_iv);
            TextView goodNameTv = (TextView) goodView.findViewById(R.id.good_name_tv);
            TextView goodNumTv = (TextView) goodView.findViewById(R.id.good_num_tv);
            TextView goodPriceTv = (TextView) goodView.findViewById(R.id.good_price_tv);
            CommonUntil.showImageInIv(this, goodInfo.getImage(), goodImageIv);
            goodNumTv.setText("X" + goodInfo.getPickNum());
            goodNameTv.setText(goodInfo.getName());
            goodPriceTv.setText("￥" + (double) (goodInfo.getPrice() * goodInfo.getPickNum()) / 100);
            detailLl.addView(goodView, index++);
            totalMoney += (goodInfo.getPrice() * goodInfo.getPickNum());

            BuyGoodItem buyGoodItem = new BuyGoodItem();
            buyGoodItem.setGoods_id(goodInfo.getGoods_id());
            buyGoodItem.setQuantity(goodInfo.getPickNum());
            buyGoods.add(buyGoodItem);
        }
        double total = (double)totalMoney / 100;
        totalTv.setText(getString(R.string.total) + "￥" + total);
        moneyTv.setText("￥" + total);
    }

    private void initPickTimeDialog(ArrayList<DeliverTime> times){
        PickTimeItemAdapter adapter = new PickTimeItemAdapter(this,times);
        int height = getResources().getDimensionPixelOffset(R.dimen.y500);
        pickTimeDialog = DialogPlus.newDialog(this)
                .setAdapter(adapter)
                .setExpanded(false)
                .setContentHolder(new ListHolder())
                .setGravity(Gravity.BOTTOM)
                .setCancelable(true)
                .setHeader(R.layout.pick_time_dialog_header)
                .setContentWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                .setContentHeight(height)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialogPlus, Object o, View view, int i) {
                        if(i != -1){
                            DeliverTime time = (DeliverTime) o;
                            timeTv.setText(time.getTime());
                            pickTimeDialog.dismiss();
                        }
                    }
                })
                .create();
    }

    private boolean isValiad(){
        if(!pickAddress && timeTv.getText().toString().trim().length() == 0){
            showToast(getString(R.string.plz_pick_address_and_time));
            return false;
        }

        if(!pickAddress){
            showToast(getString(R.string.plz_pick_address));
            return false;
        }

        if(timeTv.getText().toString().trim().length() == 0){
            showToast(getString(R.string.plz_pick_time));
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
    public void showNormal(ArrayList<DeliverTime> times) {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        if(circleProgress != null){
            circleProgress.stopAnim();
        }
        initPickTimeDialog(times);
    }

    @Override
    public void showEmpty() {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        if(circleProgress != null){
            circleProgress.stopAnim();
        }
    }

    @Override
    public void showOrderProcess() {
        sweetAlertDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#F99C35"));
        sweetAlertDialog.setTitleText("");
        sweetAlertDialog.setContentText(getString(R.string.in_create_order));
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.show();
    }

    @Override
    public void showOrderFail() {
        if(sweetAlertDialog != null){
            sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setContentText(getString(R.string.create_order_fail));
            sweetAlertDialog.setTitleText("");
        }
    }

    @Override
    public void showOrderSuccess(String data) {
        if(sweetAlertDialog != null){
            sweetAlertDialog.dismiss();
        }

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PaymentActivity.EXTRA_CHARGE,data);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }
}
