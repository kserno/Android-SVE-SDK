package com.deluxe.testapp.connect;

import com.deluxe.sveapi.utils.QueryParams;
import com.deluxe.svesdk.ApiCallback;
import com.deluxe.svesdk.SdkManager;
import com.deluxe.svesdk.model.session.SessionModel;
import com.deluxe.testapp.Backend;
import com.google.common.base.Strings;


import javax.inject.Inject;

/**
 * Created by filipsollar on 22.1.18.
 */

public class ConnectPresenter {

    private static final String TAG = ConnectPresenter.class.getSimpleName();

    private SdkManager mSdkManager;

    private Backend[] mBackends = {
            new Backend("SVE_PROD_32","prod-3.2","http://cfe.sve32-prod.datahub-testzone.com", "http://cfe.sve32-prod.datahub-testzone.com"),
            new Backend("SVE_PROD_31", "prod-3.1","http://cfe.sve31-prod.datahub-testzone.com", "http://cfe.sve31-prod.datahub-testzone.com"),
            new Backend("SVE_PROD_31_SEC", "prod-3.1-secure","http://cfe.sve31-prod.datahub-testzone.com", "http://cfe.sve31-prod.datahub-testzone.com"),
            new Backend("SVE_TEST_1", "sve-test1","http://cfe.sve-test1.datahub-testzone.com/ClientInterface", "http://cfe.sve-test1.datahub-testzone.com/nps/"),
            new Backend("SVE_TEST_2", "sve-test2","http://cfe.sve-test2.datahub-testzone.com/ClientInterface", "http://cfe.sve-test2.datahub-testzone.com/nps/"),
            new Backend("SVE_CI", "CI","http://cfe.sve-ci.datahub-testzone.com", "http://cfe.sve-ci.datahub-testzone.com"),
            new Backend("SVE_TEST_30", "3.0 TEST","http://playout.sve30-test.datahub-testzone.com", "http://playout.sve30-test.datahub-testzone.com"),
            new Backend("SVE_NPVR_31", "nPVR","http://cfe.sve31-npvr.datahub-testzone.com", "http://cfe.sve31-npvr.datahub-testzone.com")
    };

    @Inject
    public ConnectPresenter(SdkManager sdkManager) {
        mSdkManager = sdkManager;
    }

    public void onCreate(ConnectScreen screen) {
        screen.setBackend(mBackends[0]);
    }

    public void onSelectBackendClicked(ConnectScreen screen) {
        CharSequence[] items = new CharSequence[mBackends.length];
        for (int i = 0; i < items.length; i++) {
            items[i] = mBackends[i].getLabel();
        }

        screen.showBackendSelectionDialog(items);
    }

    public void onSelectDeviceTypeClicked(ConnectScreen screen) {
        screen.showDeviceTypeSelectionDialog();
    }

    public void onSelectTenantIdClicked(ConnectScreen screen) {
        screen.showTenantIdSelectionDialog();
    }

    public void onBackendSelected(ConnectScreen screen, int index) {
        screen.setBackend(mBackends[index]);
    }

    public void onResetClicked(ConnectScreen screen) {
        screen.reset();
    }


    public void connect(final ConnectScreen screen,
                        String playoutUrl,
                        String npsUrl,
                        String tenantId,
                        String deviceType,
                        String deviceId) {

        screen.showLoading();

        mSdkManager.setEndpoints(
                fixUrl(playoutUrl),
                fixUrl(npsUrl),
                fixUrl(npsUrl)
        );

        if (!Strings.isNullOrEmpty(tenantId)) {
            mSdkManager.getSdkData().getGlobalQueryParams().put(QueryParams.TENANT_ID, tenantId);
        }
        if (!Strings.isNullOrEmpty(deviceType)) {
            mSdkManager.getSdkData().getGlobalQueryParams().put(QueryParams.D_TYPE, deviceType);
            mSdkManager.getSdkData().getGlobalQueryParams().put(QueryParams.DEVICE_TYPE, deviceType);
        }
        if (!Strings.isNullOrEmpty(deviceId)) {
            mSdkManager.getSdkData().getGlobalQueryParams().put(QueryParams.D_ID, deviceId);
            mSdkManager.getSdkData().getGlobalQueryParams().put(QueryParams.V_D_ID, deviceId);
        }

        mSdkManager.aquireSession(new ApiCallback<SessionModel>() {
            @Override
            public void onResponse(SessionModel response) {
                if (response.isSuccess()) {
                    screen.success();
                } else {
                    screen.connectionError(response.getCode());
                }
                screen.hideLoading();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                screen.connectionError(t.getMessage());
                screen.hideLoading();
            }
        });


    }

    private String fixUrl(String url) {
        if (url.endsWith("/")) {
            return url;
        }
        return url + "/";
    }

}
