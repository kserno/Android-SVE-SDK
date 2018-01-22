package com.deluxe.svesdk.model.session;

import java.io.Serializable;

public class SessionModel implements Serializable {

    private boolean success;
    private String code;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
