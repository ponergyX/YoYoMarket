package dreamspace.com.yoyomarket.ui.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kennyc.view.MultiStateView;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.api.entity.element.GoodInfo;
import dreamspace.com.yoyomarket.common.VerticalSpaceItemDecoration;
import dreamspace.com.yoyomarket.common.adapter.CartItemAdapter;
import dreamspace.com.yoyomarket.common.adapter.SearchCartItemAdapter;
import dreamspace.com.yoyomarket.common.adapter.SearchGoodItemAdapter;
import dreamspace.com.yoyomarket.common.base.BaseActivity;
import dreamspace.com.yoyomarket.common.untils.ToastUntil;
import dreamspace.com.yoyomarket.ui.presenter.activity.SearchGoodActivityPresenter;
import dreamspace.com.yoyomarket.ui.view.activity.SearchView;
import dreamspace.com.yoyomarket.widget.circleProgress.CircleProgress;

/**
 * Created by Lx on 2016/2/24.
 */
public class SearchGoodActivity extends BaseActivity implements SearchView{

    public static final String SUP_ID = "SUP_ID";
    public static final String PICK_GOODS = "PICK_GOODS";

    @Bind(R.id.back_home)
    ImageView backHomeIv;

    @Bind(R.id.search_et)
    EditText searchEt;

    @Bind(R.id.search_btn)
    TextView searchBtn;

    @Bind(R.id.search_recyclerview)
    RecyclerView searchRecyclerview;

    @Bind(R.id.search_multi_state_view)
    MultiStateView searchMultiStateView;

    @Bind(R.id.cart_iv)
    ImageView cartIv;

    @Bind(R.id.money_tv)
    TextView moneyTv;

    @Bind(R.id.pick_finish_tv)
    TextView pickFinishTv;

    @Bind(R.id.pick_rl)
    RelativeLayout pickRl;

    @Bind(R.id.bottom_bar)
    RelativeLayout bottomBar;

    @Inject
    SearchGoodActivityPresenter searchGoodActivityPresenter;

    private CircleProgress circleProgress;
    private View cartView;
    private RelativeLayout cartRl;
    private RecyclerView cartRecyclerview;
    private LinearLayout cleanLl;
    private SearchCartItemAdapter cartAdapter;
    private SearchGoodItemAdapter goodAdapter;
    private HashMap<String,GoodInfo> pickGoods;
    private String supId;

    public static Intent getCallingIntent(@NonNull Context context,String supId,HashMap<String,GoodInfo> pickGoods){
        Intent intent = new Intent(context,SearchGoodActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(PICK_GOODS, pickGoods);
        bundle.putSerializable(SUP_ID, supId);
        intent.putExtras(bundle);
        return intent;
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
        searchGoodActivityPresenter.onDestory();
        if(cartAdapter != null){
            cartAdapter.unRegisterBus();
        }

        if(goodAdapter != null){
            goodAdapter.unRegisterBus();
        }
    }

    private void initInjector(){
        getApiComponent().inject(this);
    }

    private void initPresenter(){
        searchGoodActivityPresenter.attchView(this);
        searchGoodActivityPresenter.onCreate();
    }

    @Override
    protected void initViewsAndEvents() {
        initMultiStateView();
        Bundle bundle = getIntent().getExtras();
        pickGoods = (HashMap<String, GoodInfo>) bundle.getSerializable(PICK_GOODS);
        supId = bundle.getString(SUP_ID);
        cartAdapter = new SearchCartItemAdapter(this);
        goodAdapter = new SearchGoodItemAdapter(this);
        cartAdapter.setOnCartCleanListener(new SearchCartItemAdapter.OnCartCleanListener() {
            @Override
            public void onCartClean() {
                cartView.setVisibility(View.GONE);
            }
        });
        cartAdapter.setOnTotalMoneyChangeListener(new SearchCartItemAdapter.OnTotalMoneyChangeListener() {
            @Override
            public void onTotalMoneyChange(int totalMoney) {
                if(totalMoney > 0){
                    pickFinishTv.setEnabled(true);
                    pickFinishTv.setBackgroundResource(R.drawable.btn_orange_bg);
                }else{
                    pickFinishTv.setEnabled(false);
                    pickFinishTv.setBackgroundResource(R.drawable.btn_gray_bg);
                }
                moneyTv.setText("￥" + (double) totalMoney / 100);
            }
        });

        cartAdapter.setOnResizeRecyclerViewListener(new SearchCartItemAdapter.OnResizeRecyclerViewListener() {
            @Override
            public void onResizeRecyclerView() {
                resizeCartRecyclerview();
            }
        });

        moneyTv.setText("￥0.0");
        pickFinishTv.setEnabled(false);
        pickFinishTv.setBackgroundResource(R.drawable.btn_gray_bg);
        searchBtn.setEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.y2));
        searchRecyclerview.setLayoutManager(linearLayoutManager);
        searchRecyclerview.addItemDecoration(itemDecoration);
        searchRecyclerview.setAdapter(goodAdapter);
        searchRecyclerview.setHasFixedSize(true);
        cartAdapter.setData(pickGoods);
        goodAdapter.setPickGoods(pickGoods);

        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length() == 0 || s.toString().trim().equals("")){
                    searchBtn.setEnabled(false);
                    searchBtn.setBackgroundResource(R.drawable.btn_gray_bg);
                }else{
                    searchBtn.setEnabled(true);
                    searchBtn.setBackgroundResource(R.drawable.btn_orange_bg);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initMultiStateView(){
        View errorView = searchMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR);
        Button retryBtn = (Button) errorView.findViewById(R.id.retry_btn);
        retryBtn.setVisibility(View.GONE);

        View loadingView = searchMultiStateView.getView(MultiStateView.VIEW_STATE_LOADING);
        circleProgress = (CircleProgress) loadingView.findViewById(R.id.circleProgress);
    }

    private void initCartView(){
        cartView = getLayoutInflater().inflate(R.layout.view_shopping_cart,pickRl,false);
        cartRecyclerview = (RecyclerView) cartView.findViewById(R.id.cart_recyclerview);
        cleanLl = (LinearLayout) cartView.findViewById(R.id.clean_cart_ll);
        cartRl = (RelativeLayout) cartView.findViewById(R.id.cart_rl);
        pickRl.addView(cartView, pickRl.getWidth(), pickRl.getHeight() - bottomBar.getHeight());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.x2));
        cartRecyclerview.setLayoutManager(linearLayoutManager);
        cartRecyclerview.addItemDecoration(itemDecoration);
        cartRecyclerview.setAdapter(cartAdapter);

        cartView.setVisibility(View.GONE);
        cartRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartView.setVisibility(View.GONE);
            }
        });

        cleanLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartAdapter.cleanShoppingCart();
            }
        });
    }

    private void resizeCartRecyclerview(){
        if(cartAdapter.getItemCount() >= 8){
            cartRecyclerview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    getResources().getDimensionPixelSize(R.dimen.y78) * 8
                            + getResources().getDimensionPixelSize(R.dimen.x2) * 7));
        }else{
            cartRecyclerview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    getResources().getDimensionPixelSize(R.dimen.y78)
                            * cartAdapter.getItemCount()
                            + getResources().getDimensionPixelSize(R.dimen.x2)
                            * (cartAdapter.getItemCount() - 1)));
        }
    }

    @OnClick(R.id.back_home)
    void backHome(){
        if(cartAdapter != null){
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(PICK_GOODS,cartAdapter.getPickGoods());
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    @OnClick(R.id.search_btn)
    void search(){
        searchGoodActivityPresenter.searchGoods(supId,searchEt.getText().toString());
    }

    @OnClick(R.id.cart_iv)
    void checkCart(){
        if(cartView == null){
            initCartView();
        }

        resizeCartRecyclerview();

        if(cartView.getVisibility() == View.VISIBLE){
            cartView.setVisibility(View.GONE);
        }else if(cartAdapter.getItemCount() > 0){
            cartView.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.pick_finish_tv)
    void pickFinish(){
        navigator.navigateToPayOrderActivity(this,supId,cartAdapter.getPickGoods());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search_goods;
    }

    @Override
    public void onBackPressed() {
        if(cartAdapter != null){
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(PICK_GOODS,cartAdapter.getPickGoods());
            intent.putExtras(bundle);
            setResult(RESULT_OK,intent);
        }
        super.onBackPressed();
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
        searchMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        if(circleProgress != null){
            circleProgress.stopAnim();
        }
    }

    @Override
    public void showToast(@NonNull String s) {
        ToastUntil.showToast(s,this);
    }

    @Override
    public void showNormal(ArrayList<GoodInfo> goods) {
        searchMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        if(circleProgress != null){
            circleProgress.stopAnim();
        }
        goodAdapter.setData(goods);
    }

    @Override
    public void notFindResult() {
        searchMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        if(circleProgress != null){
            circleProgress.stopAnim();
        }
    }

    @Override
    public void showLoading() {
        searchMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        if(circleProgress != null){
            circleProgress.startAnim();
        }
    }
}
