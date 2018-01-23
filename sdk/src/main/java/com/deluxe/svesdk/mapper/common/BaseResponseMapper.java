package com.deluxe.svesdk.mapper.common;

import com.deluxe.sveapi.data.response.common.BaseResponse;
import com.deluxe.sveapi.data.response.common.Resp;
import com.deluxe.svesdk.model.session.SessionModel;

/**
 * Translator of raw response data to model.
 */
public class BaseResponseMapper {

    /**
     * Translates base response into session model.
     * @param baseResponse Input response from service.
     * @return Translated model set from base response data.
     */
    public static SessionModel toSessionModel(BaseResponse baseResponse) {
        SessionModel sessionModel = new SessionModel();

        for (Resp r : baseResponse.getRes().getResp()) {
            if (r.getCode().equals("notf_14")) {
                sessionModel.setSuccess(true);
                sessionModel.setCode(r.getCode());
                break;
            }
            if (r.getCode().equals("err_2")) {
                sessionModel.setSuccess(false);
                sessionModel.setCode(r.getCode());
                break;
            }
        }

        return sessionModel;
    }

}
