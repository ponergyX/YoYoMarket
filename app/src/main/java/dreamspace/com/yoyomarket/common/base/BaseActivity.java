package dreamspace.com.yoyomarket.common.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    @Nullable
    @Bind(R.id.back_home)
    ImageView backhome;

    public Navigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getLayoutResId() != 0){
            setContentView(getLayoutResId());
        }
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
        toolbarView.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(toolbarView);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

        if(displayBack){
            backhome.setVisibility(View.VISIBLE);
        }else{
            backhome.setVisibility(View.GONE);
        }
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
