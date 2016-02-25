package dreamspace.com.yoyomarket.ui.presenter.activity;

import javax.inject.Inject;

import dreamspace.com.yoyomarket.ui.presenter.Presenter;
import dreamspace.com.yoyomarket.ui.view.View;
import dreamspace.com.yoyomarket.ui.view.activity.SearchView;

/**
 * Created by Lx on 2016/2/24.
 */
public class SearchGoodActivityPresenter implements Presenter {
    private SearchView searchView;

    @Inject
    public SearchGoodActivityPresenter(){

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestory() {

    }

    @Override
    public void attchView(View v) {
        searchView = (SearchView) v;
    }
}
