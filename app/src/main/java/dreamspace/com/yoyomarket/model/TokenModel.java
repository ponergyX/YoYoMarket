package dreamspace.com.yoyomarket.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import dreamspace.com.yoyomarket.api.TokenApi;
import dreamspace.com.yoyomarket.api.entity.element.CodeLoginReq;
import dreamspace.com.yoyomarket.api.entity.element.CommonStatusRes;
import dreamspace.com.yoyomarket.api.entity.element.GetCodeReq;
import dreamspace.com.yoyomarket.api.entity.element.LoginReq;
import dreamspace.com.yoyomarket.api.entity.element.LoginRes;
import dreamspace.com.yoyomarket.api.entity.element.ModifyPwdReq;
import dreamspace.com.yoyomarket.api.entity.element.RegisterReq;
import dreamspace.com.yoyomarket.api.entity.element.RegisterRes;
import dreamspace.com.yoyomarket.api.entity.element.UserIdRes;
import dreamspace.com.yoyomarket.common.base.BaseModel;
import dreamspace.com.yoyomarket.common.provider.TokenProvider;
import rx.Observable;

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

    public Observable<CommonStatusRes> getCode(String phoneNum,int operation){
        GetCodeReq getCodeReq = new GetCodeReq();
        getCodeReq.setOperation(operation);
        getCodeReq.setPhone_num(phoneNum);
        return getService().getCode(getCodeReq);
    }

    public Observable<RegisterRes> register(String phoneNum,String password,String code){
        RegisterReq registerReq = new RegisterReq();
        registerReq.setPhone_num(phoneNum);
        registerReq.setPassword(password);
        registerReq.setCode(code);
        return getService().register(registerReq);
    }

    public Observable<LoginRes> login(String phoneNum,String password){
        LoginReq loginReq = new LoginReq();
        loginReq.setPhone_num(phoneNum);
        loginReq.setPassword(password);
        return getService().login(loginReq);
    }

    public Observable<CommonStatusRes> logout(){
        return getService().logout();
    }

    public Observable<LoginRes> codeLogin(String phoneNum,String code){
        CodeLoginReq codeLoginReq = new CodeLoginReq();
        codeLoginReq.setCode(code);
        codeLoginReq.setPhone_num(phoneNum);
        return getService().codeLogin(codeLoginReq);
    }

    public Observable<UserIdRes> modifyPwd(String phoneNum,String code,String pwd){
        ModifyPwdReq modifyPwdReq = new ModifyPwdReq();
        modifyPwdReq.setCode(code);
        modifyPwdReq.setPhone_num(phoneNum);
        modifyPwdReq.setPassword(pwd);
        return getService().modifyPassword(modifyPwdReq);
    }
}
