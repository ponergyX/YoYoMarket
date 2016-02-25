package dreamspace.com.yoyomarket.ui.views.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kennyc.view.MultiStateView;

import butterknife.Bind;
import butterknife.OnClick;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.common.base.BaseActivity;
import dreamspace.com.yoyomarket.ui.view.activity.SearchView;

/**
 * Created by Lx on 2016/2/24.
 */
public class SearchGoodActivity extends BaseActivity implements SearchView{

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        initInjector();
        initPresenter();
    }

    private void initInjector(){
        getApiComponent().inject(this);
    }

    private void initPresenter(){

    }

    @Override
    protected void initViewsAndEvents() {

    }

    @OnClick(R.id.search_btn)
    void search(){

    }

    @OnClick(R.id.cart_iv)
    void checkCart(){

    }

    @OnClick(R.id.pick_finish_tv)
    void pickFinish(){

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search_goods;
    }
}
