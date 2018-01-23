package com.deluxe.sveapi.session;

import com.deluxe.sveapi.data.request.session.LoginRequest;
import com.deluxe.sveapi.data.response.common.BaseResponse;
import com.deluxe.sveapi.data.response.session.SessionResponse;

import java.util.Hashtable;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Single;

/**
 * Created by filipsollar on 23.1.18.
 */

public interface RxSessionService {

    @GET("/ClientInterface/{version}/isVersionSupported.ashx")
    Single<BaseResponse> isVersionSupported(
            @Path("version") String version,
            @Query("m_type") String mType,
            @QueryMap Hashtable<String, String> params);

    @POST("/ClientInterface/{version}/login.ashx")
    Single<SessionResponse> login(
            @Path("version") String version,
            @Body LoginRequest loginRequest,
            @Query("m_type") String mType);
}
