package dreamspace.com.yoyomarket.ui.views.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kennyc.view.MultiStateView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.api.entity.element.GoodInfo;
import dreamspace.com.yoyomarket.common.VerticalSpaceItemDecoration;
import dreamspace.com.yoyomarket.common.adapter.CartItemAdapter;
import dreamspace.com.yoyomarket.common.adapter.CatalogItemAdapter;
import dreamspace.com.yoyomarket.common.adapter.GoodItemAdapter;
import dreamspace.com.yoyomarket.common.base.BaseActivity;
import dreamspace.com.yoyomarket.common.untils.ToastUntil;
import dreamspace.com.yoyomarket.ui.presenter.activity.PickGoodsActivityPresenter;
import dreamspace.com.yoyomarket.ui.view.activity.PickGoodsView;
import dreamspace.com.yoyomarket.widget.circleProgress.CircleProgress;

/**
 * Created by Lx on 2016/2/4.
 */
public class PickGoodsActivity extends BaseActivity implements PickGoodsView{

    public static final String SUP_ID = "SUP_ID";
    public static final String SUP_NAME = "SUPER_NAME";

    @Bind(R.id.pick_rl)
    RelativeLayout pickRl;

    @Bind(R.id.market_multi_state_view)
    MultiStateView multiStateView;

    @Bind(R.id.shop_iv)
    ImageView shopIv;

    @Bind(R.id.search_good_ll)
    LinearLayout searchGoodLl;

    @Bind(R.id.catalog_recyclerview)
    RecyclerView catalogRecyclerview;

    @Bind(R.id.goods_recyclerview)
    RecyclerView goodsRecyclerview;

    @Bind(R.id.cart_iv)
    ImageView cartIv;

    @Bind(R.id.money_tv)
    TextView priceTv;

    @Bind(R.id.pick_finish_tv)
    TextView pickFinishTv;

    @Bind(R.id.bottom_bar)
    RelativeLayout bottomBar;

    @Bind(R.id.circle_iv)
    ImageView circleIv;

    @Bind(R.id.good_rv_rl)
    RelativeLayout goodRvRl;

    @Bind(R.id.search_rl)
    RelativeLayout searchRl;

    @Bind(R.id.toolbar)
    Toolbar toolBar;

    @Inject
    PickGoodsActivityPresenter pickGoodsActivityPresenter;

    private CatalogItemAdapter catalogItemAdapter;
    private GoodItemAdapter goodItemAdapter;
    private View cartView;
    private RecyclerView cartRecyclerview;
    private LinearLayout cleanLl;
    private CartItemAdapter cartAdapter;
    private RelativeLayout cartRl;
    private CircleProgress circleProgress;
    private String supName;
    private String supId;
    private AnimatorSet animatorSet = new AnimatorSet();
    private int lastPosition = 0;

    private static final int SREACH_GOOD = 1;

    public static Intent getCallingIntent(Context context,String supId,String supName){
        Intent intent = new Intent(context,PickGoodsActivity.class);
        intent.putExtra(SUP_ID, supId);
        intent.putExtra(SUP_NAME, supName);
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
        pickGoodsActivityPresenter.onDestory();
        if(goodItemAdapter != null){
            goodItemAdapter.unRegisterBus();
        }

        if(cartAdapter != null){
            cartAdapter.unRegisterBus();
        }
    }

    private void initInjector(){
        getApiComponent().inject(this);
    }

    private void initPresenter(){
        pickGoodsActivityPresenter.attchView(this);
    }

    @Override
    protected void initViewsAndEvents() {
        supId = getIntent().getStringExtra(SUP_ID);
        supName = getIntent().getStringExtra(SUP_NAME);
        initMutliStateView();
        setTitle(supName);
        priceTv.setText("￥0.0");
        pickFinishTv.setEnabled(false);
        pickFinishTv.setBackgroundResource(R.drawable.btn_gray_bg);
        shopIv.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        final LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.y2));
        catalogItemAdapter = new CatalogItemAdapter(this);
        goodItemAdapter = new GoodItemAdapter(this);
        cartAdapter = new CartItemAdapter(this);
        catalogRecyclerview.setLayoutManager(linearLayoutManager1);
        catalogRecyclerview.addItemDecoration(itemDecoration);
        catalogRecyclerview.setAdapter(catalogItemAdapter);
        catalogRecyclerview.setHasFixedSize(true);
        goodsRecyclerview.setLayoutManager(linearLayoutManager2);
        goodsRecyclerview.addItemDecoration(itemDecoration);
        goodsRecyclerview.setAdapter(goodItemAdapter);
        goodsRecyclerview.setHasFixedSize(true);

        goodItemAdapter.setOnGoodAddClickListener(new GoodItemAdapter.OnGoodAddClickListener() {
            @Override
            public void onAddClick(int[] locations) {
                layoutCircleIv(locations);
            }
        });

        goodItemAdapter.setOnMoneyChangeListener(new GoodItemAdapter.OnMoneyChangeListener() {
            @Override
            public void onMoneyChange(int money) {
                if(money > 0){
                    pickFinishTv.setEnabled(true);
                    pickFinishTv.setBackgroundResource(R.drawable.btn_orange_bg);
                }else{
                    pickFinishTv.setEnabled(false);
                    pickFinishTv.setBackgroundResource(R.drawable.btn_gray_bg);
                }
                priceTv.setText("￥" + (double) money / 100);
            }
        });

        catalogItemAdapter.setCatalogOnClickListener(new CatalogItemAdapter.CatalogOnClickListener() {
            @Override
            public void onClick(int catalogPosition) {
                if(goodsRecyclerview.getScrollState() != RecyclerView.SCROLL_STATE_IDLE){
                    goodsRecyclerview.stopScroll();
                }
                linearLayoutManager2.scrollToPositionWithOffset(goodItemAdapter.getCatalogTitlePosition(catalogPosition), 0);
            }
        });

        cartAdapter.setOnResizeRecyclerViewListener(new CartItemAdapter.OnResizeRecyclerViewListener() {
            @Override
            public void onResizeRecyclerView() {
                resizeCartRecyclerview();
            }
        });

        cartAdapter.setOnCartCleanListener(new CartItemAdapter.OnCartCleanListener() {
            @Override
            public void onCartClean() {
                cartView.setVisibility(View.GONE);
            }
        });

        goodsRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean down = true;
            boolean scroll = false;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(scroll){
                    ArrayList list = goodItemAdapter.getCatalogTitlePositionList();
                    int firstVisiblePosition = linearLayoutManager2.findFirstVisibleItemPosition();
                    if(dy >= 0){
                        if(!down){
                            lastPosition++;
                        }
                        down = true;
                        if(firstVisiblePosition >= (int)list.get(lastPosition)) {
                            catalogItemAdapter.setSelect(lastPosition);
                            lastPosition++;
                        }
                    }else{
                        down = false;
                        if(firstVisiblePosition < (int)list.get(lastPosition)){
                            catalogItemAdapter.setSelect(--lastPosition);
                        }
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_DRAGGING){
                    scroll = true;
                    ArrayList list = goodItemAdapter.getCatalogTitlePositionList();
                    int firstPosition = linearLayoutManager2.findFirstVisibleItemPosition();
                    for(int i = 0;i < list.size() - 1;i++){
                        if(firstPosition >= (int)list.get(i) && firstPosition < (int)list.get(i + 1)){
                            lastPosition = i;
                            break;
                        }
                    }
                }else if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    scroll = false;
                }
            }
        });
    }

    private void initMutliStateView(){
        View errorView = multiStateView.getView(MultiStateView.VIEW_STATE_ERROR);
        Button retryBtn = (Button) errorView.findViewById(R.id.retry_btn);
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });

        View loadingView = multiStateView.getView(MultiStateView.VIEW_STATE_LOADING);
        circleProgress = (CircleProgress) loadingView.findViewById(R.id.circleProgress);
    }

    private void loadData(){
        pickGoodsActivityPresenter.loadData(supId);
    }

    private void layoutCircleIv(int[] locations){
        if(animatorSet.isRunning()){
            return;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Rect rectangle= new Rect();
        Window window= getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight= rectangle.top;
        int margin_top_y = locations[1] - toolBar.getHeight() - statusBarHeight;
        int margin_right_x = getResources().getDimensionPixelOffset(R.dimen.x45);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.setMargins(0, margin_top_y, margin_right_x, 0);
        circleIv.setTranslationX(0f);
        circleIv.setTranslationY(0f);
        circleIv.setLayoutParams(layoutParams);
        circleIv.setVisibility(View.VISIBLE);
        playAnimationOnCircleIv(margin_top_y, margin_right_x);
    }

    private void playAnimationOnCircleIv(int marginY,int marginX){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(circleIv, "translationY", -50);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(circleIv,"translationY",bottomBar.getTop() - marginY);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(circleIv,"translationX",-(bottomBar.getWidth() - cartIv.getRight() - marginX));
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(circleIv, "translationX", -370);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator1.setInterpolator(new AccelerateInterpolator());
        objectAnimator.setDuration(300);
        objectAnimator3.setDuration(300);
        objectAnimator1.setDuration(600);
        objectAnimator2.setDuration(600);
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimator1, objectAnimator2);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                circleIv.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    @OnClick(R.id.shop_iv)
    void checkShopInfo(){
        navigator.navigateToShopInfoActivity(this);
    }

    @OnClick(R.id.search_good_ll)
    void searchGood(){
        if(cartView != null && cartView.getVisibility() == View.VISIBLE){
            cartView.setVisibility(View.GONE);
        }
        navigator.navigateToSearchGoodActivity(this, SREACH_GOOD, supId, goodItemAdapter.getPickGoods());
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
        navigator.navigateToPayOrderActivity(this,supId,goodItemAdapter.getPickGoods());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_pick_goods;
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
        cartRecyclerview.setHasFixedSize(true);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SREACH_GOOD && resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            goodItemAdapter.setPickGoods((HashMap<String, GoodInfo>) bundle.getSerializable(SearchGoodActivity.PICK_GOODS));
        }
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
        ToastUntil.showToast(s, this);
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
    public void showNormal(HashMap<String, ArrayList<GoodInfo>> goods) {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        if(circleProgress != null){
            circleProgress.stopAnim();
        }
        Iterator<String> iterator = goods.keySet().iterator();
        ArrayList<String> catalogs = new ArrayList<>();
        catalogs.add(getString(R.string.sale_count_rank));
        while (iterator.hasNext()){
            String s = iterator.next();
            if(!s.equals(getString(R.string.sale_count_rank))){
                catalogs.add(s);
            }
        }
        catalogItemAdapter.setData(catalogs);
        goodItemAdapter.setData(goods);
    }

    @Override
    public void showEmpty() {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        if(circleProgress != null){
            circleProgress.stopAnim();
        }
    }
}
