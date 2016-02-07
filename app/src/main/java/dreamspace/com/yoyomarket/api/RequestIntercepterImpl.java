package dreamspace.com.yoyomarket.api;

import android.support.annotation.Nullable;

import java.io.IOException;

import dreamspace.com.yoyomarket.common.provider.TokenProvider;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Lx on 2016/1/28.
 */
public class RequestIntercepterImpl implements Interceptor {
    @Nullable
    private TokenProvider tokenProvider;

    @Override
    public Response intercept(Chain chain) throws IOException {
        if(tokenProvider != null){
            Request request = chain.request().newBuilder().addHeader("Access_token",getToken()).build();
            return chain.proceed(request);
        }
        return null;
    }

    public void setTokenProvider(@Nullable TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public String getToken(){
        if(tokenProvider != null){
            return tokenProvider.getToken();
        }
        return null;
    }
}
