package com.deluxe.testapp.di;

import com.deluxe.svesdk.SdkManager;
import com.deluxe.testapp.Router;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * Created by filipsollar on 22.1.18.
 */
@Module
public class ConnectModule {

    private Router mRouter;

    public ConnectModule(Router router) {
        mRouter = router;
    }

    @Provides
    public Router providesRouter() {
        return mRouter;
    }


}
