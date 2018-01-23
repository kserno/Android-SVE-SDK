package com.deluxe.svesdk;

import com.deluxe.sveapi.ApiManager;
import com.deluxe.sveapi.data.request.session.LoginRequest;
import com.deluxe.sveapi.data.response.common.BaseResponse;
import com.deluxe.sveapi.data.response.session.SessionResponse;
import com.deluxe.sveapi.utils.QueryParams;
import com.deluxe.svesdk.mapper.common.BaseResponseMapper;
import com.deluxe.svesdk.model.session.SessionModel;
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
     * @param domain Domain part of server url
     * @param port   Port part of server url
     */
    public SdkManager(String domain, String port) {
//        String domain = "http://cfe.sve-test2.datahub-testzone.com";
//        String port = "8080";
        this.domain = domain;
        this.port = port;

        sdkData = new SdkData();
        apiManager = new ApiManager(domain, port);

        globalQueryParams = new HashMap<>();
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
