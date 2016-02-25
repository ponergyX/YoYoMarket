package dreamspace.com.yoyomarket.injector.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dreamspace.com.yoyomarket.common.provider.GuestTokenProvider;
import dreamspace.com.yoyomarket.common.provider.UserTokenProvider;
import dreamspace.com.yoyomarket.common.untils.PreferenceUntil;
import dreamspace.com.yoyomarket.model.GoodsModel;
import dreamspace.com.yoyomarket.model.MarketModel;
import dreamspace.com.yoyomarket.model.OrderModel;
import dreamspace.com.yoyomarket.model.TokenModel;
import dreamspace.com.yoyomarket.model.UserModel;

/**
 * Created by Lx on 2016/1/27.
 */
@Module(includes = AppModule.class)
public class ApiModule {
    @Provides
    @Singleton
    TokenModel provideTokenModel(){
        return new TokenModel(new GuestTokenProvider());
    }

    @Provides
    @Singleton
    UserModel provideUserModel(){
        return new UserModel(new GuestTokenProvider());
    }

    @Provides
    @Singleton
    MarketModel provideMarketModel(){
        return new MarketModel(new GuestTokenProvider());
    }

    @Provides
    @Singleton
    GoodsModel provideGoodsModel(){
        return new GoodsModel(new GuestTokenProvider());
    }

    @Provides
    @Singleton
    OrderModel provideOrderModel(){
        return new OrderModel(new GuestTokenProvider());
    }
}
