package dreamspace.com.yoyomarket.ui.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;


import butterknife.Bind;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.common.base.BaseActivity;
import dreamspace.com.yoyomarket.ui.views.fragment.NormalLoginFragment;
import dreamspace.com.yoyomarket.ui.views.fragment.PhoneLoginFragment;

/**
 * Created by Lx on 2016/2/2.
 */
public class LoginActivity extends BaseActivity{

    @Bind(R.id.login_stlayout)
    SmartTabLayout tabLayout;

    @Bind(R.id.login_viewpager)
    ViewPager viewPager;

    public static Intent getCallingIntent(Context context){
        return new Intent(context,LoginActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViewsAndEvents() {
        setTitle(getString(R.string.login));
        initTabLayout();
    }

    private void initTabLayout(){
        FragmentPagerItems pagerItems = FragmentPagerItems.with(this)
                .add(R.string.phone_login, PhoneLoginFragment.class)
                .add(R.string.normal_login, NormalLoginFragment.class)
                .create();

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(),pagerItems);
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }
}
