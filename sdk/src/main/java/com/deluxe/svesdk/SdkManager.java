package com.deluxe.svesdk;

import com.deluxe.sveapi.ApiManager;
import com.deluxe.sveapi.data.response.common.BaseResponse;
import com.deluxe.sveapi.data.response.common.Resp;
import com.deluxe.sveapi.utils.QueryParams;
import com.deluxe.svesdk.mapper.common.BaseResponseMapper;
import com.deluxe.svesdk.model.session.SessionModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SdkManager {

    protected String domain;
    protected String port;

    protected ApiManager apiManager;

    protected HashMap<String, String> globalQueryParams;

    public SdkManager(String domain, String port) {
        this.domain = domain;
        this.port = port;

        apiManager = new ApiManager(domain, port);

        globalQueryParams = new HashMap<>();
    }

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
            }
        });
    }

    public void autoLogin(final ApiCallback<SessionModel> apiCallback) {
        // TODO
    }

}
