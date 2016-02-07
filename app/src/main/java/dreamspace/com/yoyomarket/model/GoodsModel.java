package dreamspace.com.yoyomarket.model;

import android.support.annotation.Nullable;

import dreamspace.com.yoyomarket.api.GoodsApi;
import dreamspace.com.yoyomarket.common.base.BaseModel;
import dreamspace.com.yoyomarket.common.provider.TokenProvider;

/**
 * Created by Lx on 2016/1/30.
 */
public class GoodsModel extends BaseModel<GoodsApi>{
    public GoodsModel(@Nullable TokenProvider tokenProvider) {
        super(tokenProvider);
    }

    @Override
    protected Class<GoodsApi> getServiceClass() {
        return GoodsApi.class;
    }
}
