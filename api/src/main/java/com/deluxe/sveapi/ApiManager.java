package com.deluxe.sveapi;

import com.deluxe.sveapi.data.request.session.LoginRequest;
import com.deluxe.sveapi.data.response.common.BaseResponse;
import com.deluxe.sveapi.data.response.session.SessionResponse;
import com.deluxe.sveapi.session.Session;
import com.deluxe.sveapi.session.SessionService;
import com.deluxe.sveapi.utils.QueryParams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager implements Session {

    private static final String API_VERSION = "v10";
    private static final String API_M_TYPE = "json";

    private Retrofit retrofit;

    private SessionService sessionService;

    private OkHttpClient getHttpLogInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        Interceptor interceptorHeaders =  new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                // if we need to add headers
//                Request.Builder builder = original.newBuilder()
//                        .header("X-APIversion", apiConfig.apiVersion);

//                Request request = builder.method(newMethod, newRequestBody)
//                        .build();
//
//                return chain.proceed(request);

                return chain.proceed(original);
            }
        };
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptorHeaders)
                .addInterceptor(interceptor)
                .connectTimeout(90L, TimeUnit.SECONDS)
                .readTimeout(90L, TimeUnit.SECONDS)
                .writeTimeout(90L, TimeUnit.SECONDS)
                .build();
    }

    Retrofit getHostAdapter(String endpoint, String port) {
        return new Retrofit.Builder()
                .baseUrl(endpoint + port)
                .client(getHttpLogInterceptor())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .build();
    }

    private Gson getGson() {
        return new GsonBuilder()
//                .registerTypeAdapter(LogoutResponse.class, new LogoutResponseTypeAdapter()) -> we can register adapters here
                .create();
    }

    public ApiManager(String domain, String port) {

//        String domain = "http://cfe.sve-test2.datahub-testzone.com";
//        String port = "8080";
        retrofit = getHostAdapter(domain, port);

        sessionService = retrofit.create(SessionService.class);

    }

    @Override
    public Call<BaseResponse> isVersionSupported(String deviceType, String majorVersion, String minorVersion, String tenantId, String language) {
        Hashtable<String,String> serviceQueryParams = new Hashtable<>();
        serviceQueryParams.put(QueryParams.D_TYPE, deviceType);
        serviceQueryParams.put(QueryParams.MA_VER, majorVersion);
        serviceQueryParams.put(QueryParams.MI_VER, minorVersion);
        serviceQueryParams.put(QueryParams.TENANT_ID, tenantId);
        serviceQueryParams.put(QueryParams.LANG, language);
        return sessionService.isVersionSupported(API_VERSION, API_M_TYPE, serviceQueryParams);
    }

    @Override
    public Call<SessionResponse> login(LoginRequest loginRequest) {
        return sessionService.login(API_VERSION, loginRequest, API_M_TYPE);
    }

}