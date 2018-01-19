package com.deluxe.svesdk.session;

import com.deluxe.svesdk.data.response.common.BaseResponse;

import java.util.Hashtable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface SessionService {

    @GET("/ClientInterface/{version}/isVersionSupported.ashx")
    Call<BaseResponse> isVersionSupported(
            @Path("version") String version,
            @Query("m_type") String mType,
            @QueryMap Hashtable<String, String> params);
}
