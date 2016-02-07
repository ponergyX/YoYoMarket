package dreamspace.com.yoyomarket.common.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import dreamspace.com.yoyomarket.common.App;
import dreamspace.com.yoyomarket.common.Navigator;
import dreamspace.com.yoyomarket.injector.component.ApiComponent;
import dreamspace.com.yoyomarket.injector.component.AppComponent;

/**
 * Created by Lx on 2016/1/27.
 */
public abstract class BaseLazyFragment extends Fragment{
    protected boolean isVisible;

    public Navigator navigator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigator = getAppComponent().navigator();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResId(),container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initViewsAndEvents();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){
            isVisible = true;
            onVisible();
        }else{
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible(){
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected abstract void initViewsAndEvents();

    protected void onInvisible(){

    }

    protected abstract @LayoutRes int getLayoutResId();

    protected AppComponent getAppComponent(){
        return ((App)getActivity().getApplication()).getAppComponent();
    }

    protected ApiComponent getApiComponent(){
        return ((App)getActivity().getApplication()).getApiComponent();
    }
}
