package com.deluxe.svesdk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.deluxe.sveapi.ApiManager;
import com.deluxe.sveapi.data.request.session.LoginRequest;
import com.deluxe.sveapi.data.response.common.BaseResponse;
import com.deluxe.sveapi.data.response.session.SessionResponse;
import com.deluxe.sveapi.utils.QueryParams;
import com.deluxe.svesdk.mapper.common.BaseResponseMapper;
import com.deluxe.svesdk.model.session.SessionModel;
import com.deluxe.svesdk.utils.Consts;
import com.deluxe.svesdk.utils.SessionRequestBuilder;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Main SDK managing class.
 *
 * Provides service handling of API calls, caching of data, etc.
 */
public class SdkManager {

    protected String domain;
    protected String port;

    protected ApiManager apiManager;

    protected HashMap<String, String> globalQueryParams;

    protected SdkData sdkData;

    /**
     * Sets manager class.
     *
     * To properly setup:
     * 1: call {@link SdkManager#onApplicationCreate(String, String, String, String, Consts.DRM_SOLUTION)}
     * 2: when server endpoint are picked call {@link SdkManager#setEndpoints(String, String, String)}
     *
     * Then SDK manager will be available to proper use.
     */
    public SdkManager() {
        sdkData = new SdkData();
        apiManager = new ApiManager();

        globalQueryParams = new HashMap<>();
        queryParamsSetup();
    }

    /**
     * Initial setup of query parameters, that are usually constant for the lifecycle of this SDK manager
     */
    protected void queryParamsSetup() {
        globalQueryParams.put(QueryParams.OS_ID, Build.VERSION.RELEASE.trim());
        globalQueryParams.put(QueryParams.SC, "true");
        globalQueryParams.put(QueryParams.TIME, "60");
        globalQueryParams.put(QueryParams.LANG, "en");
        globalQueryParams.put(QueryParams.M_TYPE, "json");
        globalQueryParams.put(QueryParams.SCL_VER, "0");
        globalQueryParams.put(QueryParams.MA_VER, "4");
        globalQueryParams.put(QueryParams.MI_VER, "0");
        globalQueryParams.put(QueryParams.OMI_TYPE, "WEB_ANDROID");
    }

    /**
     * Mandatory setup - sets endpoints for services used in API manager
     * @param sveEndpoint Endpoint to sve services
     * @param npsEndpoint Endpoint to nps services
     * @param mcsEndpoint Endpoint to mcs services
     */
    public void setEndpoints(String sveEndpoint, String npsEndpoint, String mcsEndpoint) {
        apiManager.setEndpoints(sveEndpoint, npsEndpoint, mcsEndpoint);
    }

    /**
     * Mandatory setup - sets query parameters, that are usually constant for the lifecycle of this SDK manager,
     * but have to be generated from Context in Application onCreate.
     * @param deviceType  Device type that should be used in service calls
     * @param deviceId    Device id that should be used in service calls
     * @param tenantId    Tenant id that should be used in service calls
     * @param swipeType   Swipe type that should be used in service calls
     * @param drmSolution Requested Drm solution
     */
    public void onApplicationCreate(String deviceType, String deviceId, String tenantId, String swipeType, Consts.DRM_SOLUTION drmSolution) {
        globalQueryParams.put(QueryParams.TENANT_ID, tenantId);

        globalQueryParams.put(QueryParams.D_TYPE, deviceType);
        globalQueryParams.put(QueryParams.DEVICE_TYPE, deviceType);

        globalQueryParams.put(QueryParams.V_D_ID, deviceId);
        globalQueryParams.put(QueryParams.D_ID, deviceId);
        globalQueryParams.put(QueryParams.SWIPE_TYPE, swipeType);

        sdkData.setDrmSolution(drmSolution);
    }

    /**
     * Returns information of current session type.
     * @return true if user is logged in.
     */
    public  boolean isUserLoggedIn () {
        return (sdkData.getSessionType()==Consts.LOGGED_IN);
    }

    /**
     * Aggregated API calls that provide user session - either by logging-in using saved user credentials or by loading
     * anonymous session.
     * @param apiCallback Callback object to report result of call.
     */
    public void aquireSession(final ApiCallback<SessionModel> apiCallback) {
        String deviceType = globalQueryParams.get(QueryParams.D_TYPE);
        String majorVersion = globalQueryParams.get(QueryParams.MA_VER);
        String minorVersion = globalQueryParams.get(QueryParams.MI_VER);
        String tenantId = globalQueryParams.get(QueryParams.TENANT_ID);
        String language = globalQueryParams.get(QueryParams.LANG);
        Call<BaseResponse> call = apiManager.isVersionSupported(deviceType, majorVersion, minorVersion, tenantId, language);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                // TODO add common error handling -> process response.error
                if (response.body()==null)
                    return;

                BaseResponse baseResponse = response.body();
                SessionModel sessionModel = BaseResponseMapper.toSessionModel(baseResponse);

                if (sessionModel.isSuccess()) {
                    autoLogin(apiCallback);
                }
                else {
                    // report unsupported
                    apiCallback.onResponse(sessionModel);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                apiCallback.onFailure(t);
            }
        });
    }

    /**
     * Auto-login handle using saved credentials of user.
     * @param apiCallback Callback object to report result of call.
     */
    public void autoLogin(final ApiCallback<SessionModel> apiCallback) {
        // TODO pick values from persisted data
        String userId = "";
        String oneTimeToken = "";
        LoginRequest loginRequest = SessionRequestBuilder.autoLogin(sdkData, globalQueryParams, userId, oneTimeToken).buildRequestLogin();
        Call<SessionResponse> call = apiManager.login(loginRequest);
        call.enqueue(new Callback<SessionResponse>() {
            @Override
            public void onResponse(Call<SessionResponse> call, Response<SessionResponse> response) {
                // TODO add common error handling -> process response.error
                if (response.body()==null)
                    return;

                SessionResponse sessionResponse = response.body();
                SessionModel sessionModel = BaseResponseMapper.toSessionModel(sessionResponse);

                // save session id
                String sessionId = response.body().getS_id();
                globalQueryParams.put(QueryParams.S_ID, sessionId);
                sdkData.setSessionId(sessionId);

                // TODO save other values from Session response

                // TODO later perform possible relogin, ...
                apiCallback.onResponse(sessionModel);
            }

            @Override
            public void onFailure(Call<SessionResponse> call, Throwable t) {
                apiCallback.onFailure(t);
            }
        });
    }

}
