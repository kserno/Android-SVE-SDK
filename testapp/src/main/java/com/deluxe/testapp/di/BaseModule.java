package com.deluxe.testapp.di;

import com.deluxe.testapp.BaseApplication;

import dagger.Module;
import dagger.Provides;

/**
 * Created by filipsollar on 22.1.18.
 */
@Module
public class BaseModule {
    private BaseApplication mApplication;

    public BaseModule(BaseApplication application) {
        mApplication = application;
    }

    @Provides
    public BaseApplication providesBaseApplication() {
        return mApplication;
    }
}
