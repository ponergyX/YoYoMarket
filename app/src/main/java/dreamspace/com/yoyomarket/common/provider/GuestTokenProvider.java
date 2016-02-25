package dreamspace.com.yoyomarket.common.provider;

/**
 * Created by Lx on 2016/1/28.
 */
public class GuestTokenProvider implements TokenProvider{
    @Override
    public String getToken() {
        return "guest";
    }
}
