package com.deluxe.sveapi;

import com.deluxe.sveapi.data.request.session.LoginRequest;
import com.deluxe.sveapi.data.response.common.BaseResponse;
import com.deluxe.sveapi.data.response.common.Resp;
import com.deluxe.sveapi.data.response.session.SessionResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import retrofit2.Call;

import static org.junit.Assert.fail;

public class ApiManagerTest {

    private ApiManager prepareApiManager() {

        ApiManager apiManager = new ApiManager();

        apiManager.setEndpoints(
                "http://cfe.sve-test2.datahub-testzone.com/",
                "http://cfe.sve-test2.datahub-testzone.com/",
                "http://cfe.sve-test2.datahub-testzone.com/"
        );

        return apiManager;
    }

    private void assertErrorCodes(BaseResponse baseResponse) {
        if (baseResponse.getRes()==null || baseResponse.getRes().getResp()==null || baseResponse.getRes().getResp().size()==0)
            fail("Service call failed. Missing resp element(s)");
        for (Resp resp : baseResponse.getRes().getResp()) {
            if (resp.getCode().startsWith("err")) {
                fail("Service call returned error. Response code: " + resp.getCode());
            }
            else {
                System.out.println("Success isVersionSupported call. Response code: " + resp.getCode());
            }
        }
    }

    @Test
    public void isVersionSupported() throws Exception {
        System.out.println("isVersionSupported Test\n");
        ApiManager apiManager = prepareApiManager();
        String deviceType = "m_AndroidPhoneSmall";
        String majorVersion = "4";
        String minorVersion = "0";
        String tenantIt = "ee58b1d8-bc3d-4231-b420-f6ce2e9287a6";
        String language = "en";
        Call<BaseResponse> call = apiManager.isVersionSupported(deviceType, majorVersion, minorVersion, tenantIt, language);
        try {
            retrofit2.Response<BaseResponse> response = call.execute();
            if (response.body()!=null) {
                assertErrorCodes(response.body());
            }
            else {
                try {
                    fail("Service call returned error. Message: "+response.errorBody().string());
                } catch (IOException e) {
                    fail("Service call returned error. Message could not be retrieved. Code: "+response.code());
                }
            }
        }
        catch (IOException e) {
            fail("Service call failed. Message: "+e.toString());
        }
        System.out.println("\n\n");
    }

    @Test
    public void login() throws Exception {
        System.out.println("login Test\n");
        ApiManager apiManager = prepareApiManager();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.lang = "end";
        loginRequest.login_info = new LoginRequest.LoginInfo();
        loginRequest.login_info.d_id = "deviceid123456";
        loginRequest.login_info.d_type = "m_AndroidPhoneSmall";
        loginRequest.login_info.u_id = "unittest@sve.com";
        loginRequest.login_info.pw = "unittest";
        Call<SessionResponse> call = apiManager.login(loginRequest);
        try {
            retrofit2.Response<SessionResponse> response = call.execute();
            if (response.body()!=null) {
                assertErrorCodes(response.body());
            }
            else {
                try {
                    fail("Service call returned error. Message: "+response.errorBody().string());
                } catch (IOException e) {
                    fail("Service call returned error. Message could not be retrieved. Code: "+response.code());
                }
            }
        }
        catch (IOException e) {
            fail("Service call failed. Message: "+e.toString());
        }
        System.out.println("\n\n");
    }

}