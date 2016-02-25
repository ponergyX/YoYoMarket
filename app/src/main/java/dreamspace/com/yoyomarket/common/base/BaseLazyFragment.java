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

    private boolean isFirstResume = true;
    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;
    private boolean isPrepared;

    public Navigator navigator;
    protected boolean gotData = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigator = getAppComponent().navigator();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResId(), container, false);
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
    public void onResume() {
        super.onResume();
        if (isFirstResume) {
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint()) {
            onUserVisible();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            onUserInvisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
            } else {
                onUserInvisible();
            }
        }
    }

    private synchronized void initPrepare() {
        if (isPrepared) {
            onFirstUserVisible();
        } else {
            isPrepared = true;
        }
    }

    protected void onFirstUserVisible(){
        if(!gotData){
            lazyLoad();
        }
    }

    protected void onUserVisible(){

    }

    private void onFirstUserInvisible() {
    }

    protected void onUserInvisible(){

    }

    protected abstract void lazyLoad();

    protected void setGotData(){
        gotData = true;
    }

    protected abstract void initViewsAndEvents();

    protected abstract @LayoutRes int getLayoutResId();

    protected AppComponent getAppComponent(){
        return ((App)getActivity().getApplication()).getAppComponent();
    }

    protected ApiComponent getApiComponent(){
        return ((App)getActivity().getApplication()).getApiComponent();
    }
}
