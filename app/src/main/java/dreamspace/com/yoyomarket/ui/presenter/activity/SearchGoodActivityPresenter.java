package dreamspace.com.yoyomarket.ui.presenter.activity;

import android.content.Context;

import javax.inject.Inject;

import dreamspace.com.yoyomarket.api.entity.Goods;
import dreamspace.com.yoyomarket.common.untils.NetUntils;
import dreamspace.com.yoyomarket.injector.qualifier.ForApplication;
import dreamspace.com.yoyomarket.model.GoodsModel;
import dreamspace.com.yoyomarket.ui.presenter.Presenter;
import dreamspace.com.yoyomarket.ui.view.View;
import dreamspace.com.yoyomarket.ui.view.activity.SearchView;
import retrofit2.HttpException;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Lx on 2016/2/24.
 */
public class SearchGoodActivityPresenter implements Presenter {
    private SearchView searchView;
    private Context appContext;
    private GoodsModel goodsModel;
    private Subscription searchGoodsSubscription;

    @Inject
    public SearchGoodActivityPresenter(@ForApplication Context context,GoodsModel goodsModel){
        appContext = context;
        this.goodsModel = goodsModel;
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
        if(searchGoodsSubscription != null){
            searchGoodsSubscription.unsubscribe();
        }
    }

    @Override
    public void attchView(View v) {
        searchView = (SearchView) v;
    }

    public void searchGoods(String supId,String keyword){
        if(!NetUntils.isNetworkAvailable(appContext)){
            searchView.showErrorViewState();
            searchView.showNetCantUse();
            return;
        }

        searchGoodsSubscription = goodsModel.searchGoods(supId,keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        searchView.showLoading();
                    }
                })
                .subscribe(new Action1<Goods>() {
                    @Override
                    public void call(Goods goods) {
                        if(goods.getResult().size() > 0){
                            searchView.showNormal(goods.getResult());
                        }else{
                            searchView.notFindResult();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if(throwable instanceof HttpException){
                            HttpException exception = (HttpException) throwable;
                            searchView.showErrorViewState();
                            searchView.showToast(exception.getMessage());
                        }else{
                            searchView.showErrorViewState();
                            searchView.showNetError();
                        }
                    }
                });
    }
}
