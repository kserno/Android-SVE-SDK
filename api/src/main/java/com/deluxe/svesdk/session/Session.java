package com.deluxe.svesdk.session;

import com.deluxe.svesdk.data.response.common.BaseResponse;

import retrofit2.Call;

public interface Session {
    Call<BaseResponse> isVersionSupported();
}
