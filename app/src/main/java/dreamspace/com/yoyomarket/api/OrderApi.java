package dreamspace.com.yoyomarket.api;

import com.google.gson.JsonElement;

import dreamspace.com.yoyomarket.api.entity.Orders;
import dreamspace.com.yoyomarket.api.entity.element.CommonStatusRes;
import dreamspace.com.yoyomarket.api.entity.element.CreateOrderInfo;
import dreamspace.com.yoyomarket.api.entity.element.CreateOrderRes;
import dreamspace.com.yoyomarket.api.entity.element.OrderChannel;
import dreamspace.com.yoyomarket.api.entity.element.OrderInfo;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Lx on 2016/1/28.
 */
public interface OrderApi {
    @POST("supermarket/order/")
    Observable<CreateOrderRes> createOrder(@Body CreateOrderInfo orderInfo);

    @GET("supermarket/order/{order_id}")
    Observable<OrderInfo> getOrderDetail(@Path("order_id")String order_id);

    @POST("supermarket/order/{order_id}/pay/")
    Observable<JsonElement> payOrder(@Path("order_id")String order_id,@Body OrderChannel orderChannel);

    @GET("supermarket/order/user_list/")
    Observable<Orders> getOrderList(@Query("page") int page);
}
