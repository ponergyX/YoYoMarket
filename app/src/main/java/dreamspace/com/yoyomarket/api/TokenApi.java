package dreamspace.com.yoyomarket.api;

import dreamspace.com.yoyomarket.api.entity.element.CodeLoginReq;
import dreamspace.com.yoyomarket.api.entity.element.CommonStatusRes;
import dreamspace.com.yoyomarket.api.entity.element.GetCodeReq;
import dreamspace.com.yoyomarket.api.entity.element.LoginReq;
import dreamspace.com.yoyomarket.api.entity.element.LoginRes;
import dreamspace.com.yoyomarket.api.entity.element.ModifyPwdReq;
import dreamspace.com.yoyomarket.api.entity.element.RegisterReq;
import dreamspace.com.yoyomarket.api.entity.element.RegisterRes;
import dreamspace.com.yoyomarket.api.entity.element.UserIdRes;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import rx.Observable;

/**
 * Created by Lx on 2016/1/28.
 */
public interface TokenApi {
    @POST("auth/code/")
    Observable<CommonStatusRes> getCode(@Body GetCodeReq codeReq);

    @POST("user/")
    Observable<RegisterRes> register(@Body RegisterReq registerReq);

    @POST("auth/login/")
    Observable<LoginRes> login(@Body LoginReq loginReq);

    @DELETE("auth/logout/")
    Observable<CommonStatusRes> logout();

    @POST("auth/code_login/")
    Observable<LoginRes> codeLogin(@Body CodeLoginReq codeLoginReq);

    @PUT("user/reset_password/")
    Observable<UserIdRes> modifyPassword(@Body ModifyPwdReq modifyPwdReq);
}
