package com.deluxe.svesdk.mapper.common;

import com.deluxe.sveapi.data.response.common.BaseResponse;
import com.deluxe.sveapi.data.response.common.Resp;
import com.deluxe.svesdk.model.session.SessionModel;

public class BaseResponseMapper {

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
