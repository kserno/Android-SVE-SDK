package com.deluxe.testapp.di;

import com.deluxe.svesdk.SdkManager;
import com.deluxe.testapp.BaseApplication;
import com.deluxe.testapp.BaseFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by filipsollar on 22.1.18.
 */
@Module
public class BaseModule {

    private BaseApplication mApplication;
    private SdkManager mSdkManager;

    public BaseModule(BaseApplication application) {
        mApplication = application;
        mSdkManager = new SdkManager();
    }

    @Provides
    public BaseApplication providesBaseApplication() {
        return mApplication;
    }

    @Provides
    public SdkManager providesSdkManager() {
        return mSdkManager;
    }
}
