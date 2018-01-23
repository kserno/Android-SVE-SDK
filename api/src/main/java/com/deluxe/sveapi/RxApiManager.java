package com.deluxe.sveapi;

import com.deluxe.sveapi.data.request.session.LoginRequest;
import com.deluxe.sveapi.data.response.common.BaseResponse;
import com.deluxe.sveapi.data.response.session.SessionResponse;
import com.deluxe.sveapi.session.RxSession;

import rx.Single;

/**
 * Created by filipsollar on 23.1.18.
 */

public class RxApiManager implements RxSession {



    @Override
    public Single<BaseResponse> isVersionSupported(String deviceType, String majorVersion, String minorVersion, String tenantId, String language) {
        return null;
    }

    @Override
    public Single<SessionResponse> login(LoginRequest loginRequest) {
        return null;
    }
}
