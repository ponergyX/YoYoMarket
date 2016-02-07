package dreamspace.com.yoyomarket.api;

import dreamspace.com.yoyomarket.api.entity.element.CommonStatusRes;
import dreamspace.com.yoyomarket.api.entity.element.CreateOrderInfo;
import dreamspace.com.yoyomarket.api.entity.element.OrderChannel;
import dreamspace.com.yoyomarket.api.entity.element.OrderInfo;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Lx on 2016/1/28.
 */
public interface OrderApi {
    @POST("supermarket/order/")
    Observable<CommonStatusRes> createOrder(@Body CreateOrderInfo orderInfo);

    @GET("supermarket/order/{order_id}")
    Observable<OrderInfo> getOrderDetail(@Path("order_id")String order_id);

    @POST("supermarket/order/{order_id}/pay/")
    Observable<CommonStatusRes> payOrder(@Path("order_id")String order_id,@Body OrderChannel orderChannel);
}
