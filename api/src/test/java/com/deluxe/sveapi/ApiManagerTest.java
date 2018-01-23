package com.deluxe.sveapi;

import com.deluxe.sveapi.data.response.common.BaseResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import retrofit2.Call;

import static org.junit.Assert.fail;

public class ApiManagerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(System.out);
        System.setErr(System.err);
    }

    public ApiManager prepareApiManager() {

        ApiManager apiManager = new ApiManager();

        apiManager.setEndpoints(
                "http://cfe.sve-test2.datahub-testzone.com/",
                "http://cfe.sve-test2.datahub-testzone.com/",
                "http://cfe.sve-test2.datahub-testzone.com/"
        );

        return apiManager;
    }

    @Test
    public void isVersionSupported() throws Exception {
        System.out.print("isVersionSupported Test");
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
                // success
                try {
                    System.out.print("Success isVersionSupported call. Response code: " + response.body().getRes().getResp().get(0).getCode());
                }
                catch (Exception e) {
                    fail("Service call corrupted data object. Message: "+e.getMessage());
                }
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
    }

    @Test
    public void login() throws Exception {
        ApiManager apiManager = prepareApiManager();
    }

}