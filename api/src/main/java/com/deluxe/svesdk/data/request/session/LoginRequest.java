package com.deluxe.svesdk.data.request.session;

import com.deluxe.svesdk.data.request.common.CommonRequest;

public class LoginRequest extends CommonRequest {

    public String lang;
    public String app_version;
    public DeviceTypeInfo device_type_info;
    public LoginInfo login_info;

    public static class DeviceTypeInfo {
        public String manufacturer;
        public String model;
        public String release_version;
        public String build_version;
        public String product;
        public String id;
        public String device_HW;
    }

    public static class LoginInfo {
        public String u_id;
        public String pw;
        public String d_pw;
        public String oneTimeToken;
        public String socialAccountToken;
        public String social_network_type;
        public String tenantId;
        public String d_id;
        public String d_type;
        public String os_id;
        public String swipe_type;
        public String drm;
        public String v_d_id;
        public String OMI_type;
        public String cl_d_id;
        public String region;
    }

}
