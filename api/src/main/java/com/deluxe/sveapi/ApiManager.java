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

    private Retrofit retrofitSve;
    private Retrofit retrofitNps;
    private Retrofit retrofitMcs;

    // Sve services
    private SessionService sessionService;

    // TODO nps services

    // TODO mcs services

    protected OkHttpClient getHttpLogInterceptor() {
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

    Retrofit getHostAdapter(String endpoint) {
        return new Retrofit.Builder()
                .baseUrl(endpoint)
                .client(getHttpLogInterceptor())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .build();
    }

    protected Gson getGson() {
        return new GsonBuilder()
//                .registerTypeAdapter(LogoutResponse.class, new LogoutResponseTypeAdapter()) -> we can register adapters here
                .create();
    }

    /**
     * Default constructor. Endpoints must be set prior to service use, please see {@link ApiManager#setEndpoints(String, String, String)}.
     */
    public ApiManager() {

    }

    /**
     * Constructor that also sets endpoint for services used by this API manager.
     * @param sveEndpoint Endpoint to sve services
     * @param npsEndpoint Endpoint to nps services
     * @param mcsEndpoint Endpoint to mcs services
     */
    public ApiManager(String sveEndpoint, String npsEndpoint, String mcsEndpoint) {
        setEndpoints(sveEndpoint, npsEndpoint, mcsEndpoint);
    }

    /**
     * Sets endpoint for services used by this API manager.
     * @param sveEndpoint Endpoint to sve services
     * @param npsEndpoint Endpoint to nps services
     * @param mcsEndpoint Endpoint to mcs services
     */
    public void setEndpoints(String sveEndpoint, String npsEndpoint, String mcsEndpoint) {
        setEndpointSve(sveEndpoint);
        setEndpointNps(npsEndpoint);
        setEndpointMcs(mcsEndpoint);
    }

    /**
     * Sets endpoint for sve services used by this API manager.
     * @param sveEndpoint Endpoint to sve services
     */
    public void setEndpointSve(String sveEndpoint) {

        retrofitSve = getHostAdapter(sveEndpoint);

        sessionService = retrofitSve.create(SessionService.class);
    }

    /**
     * Sets endpoint for nps services used by this API manager.
     * @param npsEndpoint Endpoint to nps services
     */
    public void setEndpointNps(String npsEndpoint) {

        retrofitNps = getHostAdapter(npsEndpoint);
        // TODO create nps services
    }

    /**
     * Sets endpoint for mcs services used by this API manager.
     * @param mcsEndpoint Endpoint to mcs services
     */
    public void setEndpointMcs(String mcsEndpoint) {

        retrofitMcs = getHostAdapter(mcsEndpoint);
        // TODO create mcs services
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