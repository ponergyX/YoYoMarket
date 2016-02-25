package dreamspace.com.yoyomarket.common.provider;

import com.squareup.otto.Bus;

/**
 * Created by Lx on 2016/2/23.
 */
public final class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance(){
        return BUS;
    }
}
