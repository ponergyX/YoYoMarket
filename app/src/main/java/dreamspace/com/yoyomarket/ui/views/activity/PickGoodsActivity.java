package dreamspace.com.yoyomarket.ui.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.common.VerticalSpaceItemDecoration;
import dreamspace.com.yoyomarket.common.adapter.CatalogItemAdapter;
import dreamspace.com.yoyomarket.common.adapter.GoodItemAdapter;
import dreamspace.com.yoyomarket.common.base.BaseActivity;
import dreamspace.com.yoyomarket.ui.presenter.activity.PickGoodsActivityPresenter;
import dreamspace.com.yoyomarket.ui.view.activity.PickGoodsView;

/**
 * Created by Lx on 2016/2/4.
 */
public class PickGoodsActivity extends BaseActivity implements PickGoodsView{

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

    @Inject
    PickGoodsActivityPresenter pickGoodsActivityPresenter;

    private CatalogItemAdapter catalogItemAdapter;
    private GoodItemAdapter goodItemAdapter;

    public static Intent getCallingIntent(Context context){
        return new Intent(context,PickGoodsActivity.class);
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
        pickGoodsActivityPresenter.attchView(this);
    }

    @Override
    protected void initViewsAndEvents() {
        setTitle("东大校之友");
        shopIv.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.y2));
        catalogItemAdapter = new CatalogItemAdapter(this);
        goodItemAdapter = new GoodItemAdapter(this);
        catalogRecyclerview.setLayoutManager(linearLayoutManager1);
        catalogRecyclerview.addItemDecoration(itemDecoration);
        catalogRecyclerview.setAdapter(catalogItemAdapter);
        catalogRecyclerview.setHasFixedSize(true);
        goodsRecyclerview.setLayoutManager(linearLayoutManager2);
        goodsRecyclerview.addItemDecoration(itemDecoration);
        goodsRecyclerview.setAdapter(goodItemAdapter);
        goodsRecyclerview.setHasFixedSize(true);
    }

    @OnClick(R.id.shop_iv)
    void checkShopInfo(){
        navigator.navigateToShopInfoActivity(this);
    }

    @OnClick(R.id.search_good_ll)
    void searchGood(){

    }

    @OnClick(R.id.cart_iv)
    void checkCart(){

    }

    @OnClick(R.id.pick_finish_tv)
    void pickFinish(){

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_pick_goods;
    }
}
