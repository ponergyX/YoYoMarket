package dreamspace.com.yoyomarket.model;

import android.support.annotation.Nullable;

import com.google.gson.JsonElement;

import dreamspace.com.yoyomarket.api.OrderApi;
import dreamspace.com.yoyomarket.api.entity.Orders;
import dreamspace.com.yoyomarket.api.entity.element.CommonStatusRes;
import dreamspace.com.yoyomarket.api.entity.element.CreateOrderInfo;
import dreamspace.com.yoyomarket.api.entity.element.CreateOrderRes;
import dreamspace.com.yoyomarket.api.entity.element.OrderChannel;
import dreamspace.com.yoyomarket.api.entity.element.OrderInfo;
import dreamspace.com.yoyomarket.common.base.BaseModel;
import dreamspace.com.yoyomarket.common.provider.TokenProvider;
import rx.Observable;

/**
 * Created by Lx on 2016/1/30.
 */
public class OrderModel extends BaseModel<OrderApi>{
    public OrderModel(@Nullable TokenProvider tokenProvider) {
        super(tokenProvider);
    }

    @Override
    protected Class<OrderApi> getServiceClass() {
        return OrderApi.class;
    }

    public Observable<CreateOrderRes> createOrder(CreateOrderInfo orderInfo){
        return getService().createOrder(orderInfo);
    }

    public Observable<JsonElement> payOrder(String orderId,OrderChannel orderChannel){
        return getService().payOrder(orderId,orderChannel);
    }

    public Observable<Orders> getOrderList(int page){
        return getService().getOrderList(page);
    }

    public Observable<OrderInfo> getOrderInfo(String orderId){
        return  getService().getOrderDetail(orderId);
    }
}
