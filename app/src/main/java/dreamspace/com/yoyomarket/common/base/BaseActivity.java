package dreamspace.com.yoyomarket.common.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import dreamspace.com.yoyomarket.R;
import dreamspace.com.yoyomarket.common.App;
import dreamspace.com.yoyomarket.common.Navigator;
import dreamspace.com.yoyomarket.injector.component.ApiComponent;
import dreamspace.com.yoyomarket.injector.component.AppComponent;
import dreamspace.com.yoyomarket.injector.module.ActivityModule;

/**
 * Created by Lx on 2016/1/27.
 */
public abstract class BaseActivity extends AppCompatActivity{
    @Nullable
    @Bind(R.id.toolbar)
    Toolbar toolbarView;

    @Nullable
    @Bind(R.id.toolbar_title)
    TextView toolbarTitleTv;

    public Navigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        navigator = ((App)getApplication()).getAppComponent().navigator();
        initToolBar();
        initViewsAndEvents();
    }

    private void initToolBar(){
        if(toolbarView == null){
            return;
        }
        toolbarView.setBackgroundColor(getResources().getColor(R.color.app_color));
        setSupportActionBar(toolbarView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    protected void setTitle(String title){
        if(toolbarView == null){
            return;
        }
        toolbarTitleTv.setText(title);
    }

    protected void setDisplayBack(boolean displayBack){
        if(toolbarView == null){
            return;
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(displayBack);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public AppComponent getAppComponent(){
        return ((App)getApplication()).getAppComponent();
    }

    public ApiComponent getApiComponent(){
        return ((App)getApplication()).getApiComponent();
    }

    public ActivityModule getActivityModule(){
        return new ActivityModule(this);
    }

    protected abstract void initViewsAndEvents();

    abstract protected @LayoutRes int getLayoutResId();
}
