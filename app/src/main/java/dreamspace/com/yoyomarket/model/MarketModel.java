package dreamspace.com.yoyomarket.model;

import android.support.annotation.Nullable;

import dreamspace.com.yoyomarket.api.MarketApi;
import dreamspace.com.yoyomarket.common.base.BaseModel;
import dreamspace.com.yoyomarket.common.provider.TokenProvider;

/**
 * Created by Lx on 2016/1/30.
 */
public class MarketModel extends BaseModel<MarketApi>{
    public MarketModel(@Nullable TokenProvider tokenProvider) {
        super(tokenProvider);
    }

    @Override
    protected Class<MarketApi> getServiceClass() {
        return MarketApi.class;
    }
}
