package com.deluxe.sveapi.session;

import com.deluxe.sveapi.data.request.session.LoginRequest;
import com.deluxe.sveapi.data.response.common.BaseResponse;
import com.deluxe.sveapi.data.response.session.SessionResponse;

import retrofit2.Call;
import rx.Single;

/**
 * Created by filipsollar on 23.1.18.
 */

public interface RxSession {
    Single<BaseResponse> isVersionSupported(String deviceType, String majorVersion, String minorVersion, String tenantId, String language);
    Single<SessionResponse> login(LoginRequest loginRequest);
}
