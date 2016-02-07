package dreamspace.com.yoyomarket.api;

import dreamspace.com.yoyomarket.api.entity.Goods;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Lx on 2016/1/28.
 */
public interface GoodsApi {
    @GET("supermarket/{sup_id}/goods/list/")
    Observable<Goods> getGoodsList(@Path("sup_id")String sup_id,@Query("catalog")String catalog,@Query("page")int page);

    @GET("supermarket/{sup_id}/goods/search/")
    Observable<Goods> searchShopGoods(@Path("sup_id")String sup_id,@Query("keyword")String keyword,@Query("page")int page);
}
