package dreamspace.com.yoyomarket.model;

import android.support.annotation.Nullable;

import dreamspace.com.yoyomarket.api.OrderApi;
import dreamspace.com.yoyomarket.common.base.BaseModel;
import dreamspace.com.yoyomarket.common.provider.TokenProvider;

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
}
