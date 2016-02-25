package dreamspace.com.yoyomarket.ui.views.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import javax.inject.Inject;

import butterknife.Bind;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.common.base.BaseActivity;
import dreamspace.com.yoyomarket.ui.presenter.activity.MainActivityPresenter;
import dreamspace.com.yoyomarket.ui.view.activity.MainView;
import dreamspace.com.yoyomarket.ui.views.fragment.MarketsFragment;
import dreamspace.com.yoyomarket.ui.views.fragment.MineFragment;
import dreamspace.com.yoyomarket.ui.views.fragment.OrdersFragment;

public class MainActivity extends BaseActivity implements MainView{
    @Bind(R.id.viewpager)
    ViewPager viewPager;

    @Bind(R.id.viewpagertab)
    SmartTabLayout viewPagerTab;

    @Inject
    MainActivityPresenter mainActivityPresenter;

    public static Intent getCallingIntent(Context context){
        return new Intent(context,MainActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTabLayout();
        initInjector();
        initPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainActivityPresenter.onDestory();
    }

    @Override
    protected void initViewsAndEvents() {

    }

    private void initTabLayout(){
        final int[] tabIcons = {R.drawable.ic_market,R.drawable.ic_order,R.drawable.ic_mine};

        FragmentPagerItems pagerItems = FragmentPagerItems.with(this)
                .add(getString(R.string.tab_markets), MarketsFragment.class)
                .add(getString(R.string.tab_orders), OrdersFragment.class)
                .add(getString(R.string.tab_mine), MineFragment.class)
                .create();

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(),pagerItems);
        viewPager.setAdapter(adapter);
        viewPagerTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup viewGroup, int i, PagerAdapter pagerAdapter) {
                View tabView = getLayoutInflater().inflate(R.layout.custom_tab_layout, viewGroup, false);
                ImageView imageView = (ImageView) tabView.findViewById(R.id.tab_icon_iv);
                imageView.setImageResource(tabIcons[i]);
                return tabView;
            }
        });
        viewPagerTab.setViewPager(viewPager);
    }

    private void initInjector(){
        getApiComponent().inject(this);
    }

    private void initPresenter(){
        mainActivityPresenter.attchView(this);
        mainActivityPresenter.onCreate();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }
}
