package dreamspace.com.yoyomarket.api;

import java.util.ArrayList;

import dreamspace.com.yoyomarket.api.entity.AllComments;
import dreamspace.com.yoyomarket.api.entity.MarketCatalogs;
import dreamspace.com.yoyomarket.api.entity.Markets;
import dreamspace.com.yoyomarket.api.entity.RecentComments;
import dreamspace.com.yoyomarket.api.entity.element.CommentOrderRes;
import dreamspace.com.yoyomarket.api.entity.element.DeliverTime;
import dreamspace.com.yoyomarket.api.entity.element.MarketInfo;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Lx on 2016/1/28.
 */
public interface MarketApi {
    @GET("supermarket/list/")
    Observable<Markets> getMarketsList(@Query("page")int page,@Query("location")String location);

    @GET("supermarket/{sup_id}")
    Observable<MarketInfo> getMarketInfo(@Path("sup_id")String sup_id);

    @GET("supermarket/{sup_id}/catalog/")
    Observable<MarketCatalogs> getMarketCatalogs(@Path("sup_id")String sup_id);

    @POST("supermarket/{sup_id}/comment/")
    Observable<CommentOrderRes> commentOrder(@Path("sup_id")String sup_id);

    @GET("supermarket/{sup_id}/comment/recent/")
    Observable<RecentComments> getRecentComments(@Path("sup_id")String sup_id);

    @GET("supermarket/{sup_id}/comment/{page}")
    Observable<AllComments> getAllComments(@Path("sup_id")String sup_id,@Path("page")int page);

    @GET("send_time/")
    Observable<ArrayList<DeliverTime>> getDeliverTimes();
}
