package com.deluxe.testapp;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.deluxe.svesdk.SdkManager;
import com.deluxe.svesdk.utils.Consts;
import com.deluxe.svesdk.utils.DeviceInformation;
import com.deluxe.testapp.di.BaseComponent;
import com.deluxe.testapp.di.BaseModule;
import com.deluxe.testapp.di.DaggerBaseComponent;

import javax.inject.Inject;

/**
 * Created by filipsollar on 22.1.18.
 */

public class BaseApplication extends Application {

    private static final String DEFAULT_TENANT_ID = "ee58b1d8-bc3d-4231-b420-f6ce2e9287a6";

    private BaseComponent mComponent;

    @Inject SdkManager mSdkManager;

    @Override
    public void onCreate() {
        super.onCreate();
        initDependencyInjection();
        initSdkManager();
    }

    public BaseComponent getBaseComponent() {
        return mComponent;
    }


    private void initSdkManager() {

        String deviceType = getString(R.string.device_type);
        String deviceId = DeviceInformation.getStandardDeviceId(
                getBaseContext(),
                Utils.getIMEI(getBaseContext()),
                Utils.getSerial(getBaseContext()),
                Utils.getMacAddress(getBaseContext())
        );
        String swipeType = getString(R.string.swipe_device_type);
        
        mSdkManager.onApplicationCreate(
                deviceType,
                deviceId,
                DEFAULT_TENANT_ID,
                swipeType,
                Consts.DRM_SOLUTION.NO_DRM
        );
    }

    private void initDependencyInjection() {
        mComponent = DaggerBaseComponent.builder()
                .baseModule(new BaseModule(this))
                .build();
        mComponent.inject(this);

    }

}
