package dreamspace.com.yoyomarket.api;

import dreamspace.com.yoyomarket.api.entity.Addresses;
import dreamspace.com.yoyomarket.api.entity.element.AddressId;
import dreamspace.com.yoyomarket.api.entity.element.AddressInfo;
import dreamspace.com.yoyomarket.api.entity.element.CommonStatusRes;
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
    @POST("user/address/")
    Observable<AddressId> createGetGoodsAddress(@Body AddressInfo addressInfo);

    @DELETE("user/address/{address_id}")
    Observable<CommonStatusRes> deleteGetGoodsAddress(@Path("address_id") String address_id);

    @PUT("user/address/{address_id}")
    Observable<CommonStatusRes> modifyGetGoodsAddress(@Path("address_id") String address_id);

    @GET("user/address/{address_id}")
    Observable<AddressInfo> getGetGoodsAddress(@Path("address_id") String address_id);

    @GET("user/address/list/{page}")
    Observable<Addresses> getGetGoodsAddresses(@Path("page") int page);
}
