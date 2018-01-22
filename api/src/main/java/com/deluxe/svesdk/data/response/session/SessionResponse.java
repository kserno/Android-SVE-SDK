package com.deluxe.svesdk.data.response.session;

import com.deluxe.svesdk.data.response.common.BaseResponse;
import com.google.gson.annotations.SerializedName;

/**
 *
 */
public class SessionResponse extends BaseResponse{

    public String s_id;
    public DrmInfo drm_cl;
    public UserGroup userGroup;
    public String oneTimeToken;
    public String u_id;
    public String npsToken;

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
