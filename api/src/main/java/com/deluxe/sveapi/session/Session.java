package com.deluxe.sveapi.session;

import com.deluxe.sveapi.data.request.session.LoginRequest;
import com.deluxe.sveapi.data.response.common.BaseResponse;
import com.deluxe.sveapi.data.response.session.SessionResponse;

import retrofit2.Call;

public interface Session {
    Call<BaseResponse> isVersionSupported(String deviceType, String majorVersion, String minorVersion, String tenantId, String language);
    Call<SessionResponse> login(LoginRequest loginRequest);
}
