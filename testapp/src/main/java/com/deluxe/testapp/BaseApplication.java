package com.deluxe.testapp;

import android.app.Application;

import com.deluxe.testapp.di.BaseComponent;
import com.deluxe.testapp.di.DaggerBaseComponent;

/**
 * Created by filipsollar on 22.1.18.
 */

public class BaseApplication extends Application {

    private BaseComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDependencyInjection();
    }

    private void initDependencyInjection() {
        mComponent = DaggerBaseComponent.builder()
                .build();

    }

    public BaseComponent getBaseComponent() {
        return mComponent;
    }
}
