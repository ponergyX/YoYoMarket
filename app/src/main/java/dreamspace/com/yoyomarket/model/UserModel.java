package dreamspace.com.yoyomarket.model;

import android.support.annotation.Nullable;

import dreamspace.com.yoyomarket.api.UserApi;
import dreamspace.com.yoyomarket.api.entity.Addresses;
import dreamspace.com.yoyomarket.api.entity.UserInfo;
import dreamspace.com.yoyomarket.api.entity.element.AddressId;
import dreamspace.com.yoyomarket.api.entity.element.AddressInfo;
import dreamspace.com.yoyomarket.api.entity.element.CommonStatusRes;
import dreamspace.com.yoyomarket.api.entity.element.ImageTokenRes;
import dreamspace.com.yoyomarket.api.entity.element.SuggestionReq;
import dreamspace.com.yoyomarket.api.entity.element.SuggestionRes;
import dreamspace.com.yoyomarket.common.base.BaseModel;
import dreamspace.com.yoyomarket.common.provider.TokenProvider;
import rx.Observable;

/**
 * Created by Lx on 2016/1/30.
 */
public class UserModel extends BaseModel<UserApi>{
    public UserModel(@Nullable TokenProvider tokenProvider) {
        super(tokenProvider);
    }

    @Override
    protected Class<UserApi> getServiceClass() {
        return UserApi.class;
    }

    public Observable<ImageTokenRes> getUploadImageToken(){
        return getService().getUploadImageToken();
    }

    public Observable<CommonStatusRes> updateUserAvatar(String key){
        UserInfo userInfo = new UserInfo();
        userInfo.setImage(key);
        return getService().updateUserInfo(userInfo);
    }

    public Observable<UserInfo> getUserInfo(){
        return getService().getUserInfo();
    }

    public Observable<Addresses> getAddressList(int page){
        return getService().getGetGoodsAddresses(page);
    }

    public Observable<AddressId> createAddress(String phoneNum,String address,String sex,String name){
        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setName(name);
        addressInfo.setAddress(address);
        addressInfo.setPhone_num(phoneNum);
        addressInfo.setSex(sex);
        return getService().createGetGoodsAddress(addressInfo);
    }

    public Observable<CommonStatusRes> modifyAddress(String phoneNum,String address,String sex,String name,String addressId){
        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setName(name);
        addressInfo.setAddress(address);
        addressInfo.setPhone_num(phoneNum);
        addressInfo.setSex(sex);
        return getService().modifyGetGoodsAddress(addressId, addressInfo);
    }

    public Observable<CommonStatusRes> deleteAddress(String addressId){
        return getService().deleteGetGoodsAddress(addressId);
    }

    public Observable<SuggestionRes> suggest(SuggestionReq suggestionReq){
        return getService().suggest(suggestionReq);
    }
}
