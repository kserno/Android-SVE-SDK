package com.deluxe.svesdk.session;

import com.deluxe.svesdk.data.request.session.LoginRequest;
import com.deluxe.svesdk.data.response.common.BaseResponse;
import com.deluxe.svesdk.data.response.session.SessionResponse;

import retrofit2.Call;

public interface Session {
    Call<BaseResponse> isVersionSupported();
    Call<SessionResponse> login(LoginRequest loginRequest);
}
