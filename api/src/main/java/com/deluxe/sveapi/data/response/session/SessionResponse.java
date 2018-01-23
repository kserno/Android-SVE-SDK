package com.deluxe.sveapi.data.response.session;

import com.deluxe.sveapi.data.response.common.BaseResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Raw response data for session related services.
 */
public class SessionResponse extends BaseResponse{

    private String s_id;
    private DrmInfo drm_cl;
    private UserGroup userGroup;
    private String oneTimeToken;
    private String u_id;
    private String npsToken;

    public String getS_id() {
        return s_id;
    }

    public DrmInfo getDrm_cl() {
        return drm_cl;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public String getOneTimeToken() {
        return oneTimeToken;
    }

    public String getU_id() {
        return u_id;
    }

    public String getNpsToken() {
        return npsToken;
    }

    public static class DrmInfo {
        public DrmInfoVmx vmx_cl;
        public DrmInfoCastlabs cl_wv_cl;
    }

    public static class DrmInfoVmx {
        public String company_name;
        public String VCAS_URL;
    }

    public static class DrmInfoCastlabs {
        public String CL_URL;
        public String CL_MerchantID;
        public String CL_Portal;
        public String CL_UserID;
        public String CL_ACK_URL;
        public String CL_Heartbeat_URL;
        public CLUrls CL_URLs;
    }

    public static class CLUrls {
        public URL[] URL;
    }

    public static class URL {
        @SerializedName("-Type")
        public String type;
        @SerializedName("#text")
        public String text;
    }

    public static class UserGroup {
        public String[] group;
    }
}
