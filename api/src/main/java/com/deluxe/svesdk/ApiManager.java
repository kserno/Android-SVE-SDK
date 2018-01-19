package com.deluxe.svesdk;

import com.deluxe.svesdk.data.response.common.BaseResponse;
import com.deluxe.svesdk.session.Session;
import com.deluxe.svesdk.session.SessionService;
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

    private Retrofit retrofit;

    private SessionService menuService;

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

    public ApiManager() {

        String domain = "";
        String port = "";
        retrofit = getHostAdapter(domain, port);

        menuService = retrofit.create(SessionService.class);

    }

    @Override
    public Call<BaseResponse> isVersionSupported() {
        return menuService.isVersionSupported("version", "mtype", new Hashtable<String, String>());
    }

}