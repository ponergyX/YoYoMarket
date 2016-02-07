package dreamspace.com.yoyomarket.model;

import android.support.annotation.Nullable;

import dreamspace.com.yoyomarket.api.TokenApi;
import dreamspace.com.yoyomarket.common.base.BaseModel;
import dreamspace.com.yoyomarket.common.provider.TokenProvider;

/**
 * Created by Lx on 2016/1/30.
 */
public class TokenModel extends BaseModel<TokenApi>{

    public TokenModel(@Nullable TokenProvider tokenProvider) {
        super(tokenProvider);
    }

    @Override
    protected Class<TokenApi> getServiceClass() {
        return TokenApi.class;
    }
}
