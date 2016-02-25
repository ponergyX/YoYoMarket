package dreamspace.com.yoyomarket.common.provider;

/**
 * Created by Lx on 2016/1/28.
 */
public class UserTokenProvider implements TokenProvider{
    private String token;

    public UserTokenProvider(String token){
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }
}
