package dreamspace.com.yoyomarket.api;

import dreamspace.com.yoyomarket.api.entity.Addresses;
import dreamspace.com.yoyomarket.api.entity.UserInfo;
import dreamspace.com.yoyomarket.api.entity.element.AddressId;
import dreamspace.com.yoyomarket.api.entity.element.AddressInfo;
import dreamspace.com.yoyomarket.api.entity.element.CommonStatusRes;
import dreamspace.com.yoyomarket.api.entity.element.ImageTokenRes;
import dreamspace.com.yoyomarket.api.entity.element.SuggestionReq;
import dreamspace.com.yoyomarket.api.entity.element.SuggestionRes;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Lx on 2016/1/28.
 */
public interface UserApi {
    @POST("static/token/")
    Observable<ImageTokenRes> getUploadImageToken();

    @POST("address/")
    Observable<AddressId> createGetGoodsAddress(@Body AddressInfo addressInfo);

    @DELETE("address/{address_id}")
    Observable<CommonStatusRes> deleteGetGoodsAddress(@Path("address_id") String address_id);

    @PUT("address/{address_id}")
    Observable<CommonStatusRes> modifyGetGoodsAddress(@Path("address_id") String address_id,@Body AddressInfo addressInfo);

    @GET("address/{address_id}")
    Observable<AddressInfo> getGetGoodsAddress(@Path("address_id") String address_id);

    @GET("address/list/{page}")
    Observable<Addresses> getGetGoodsAddresses(@Path("page") int page);

    @PUT("user/")
    Observable<CommonStatusRes> updateUserInfo(@Body UserInfo userInfo);

    @GET("user/")
    Observable<UserInfo> getUserInfo();

    @POST("suggestion/")
    Observable<SuggestionRes> suggest(@Body SuggestionReq suggestionReq);
}
