package com.deluxe.svesdk.utils;

import android.os.Build;

import com.deluxe.sveapi.data.request.session.LoginRequest;
import com.deluxe.sveapi.utils.QueryParams;
import com.deluxe.svesdk.SdkData;

/**
 * Builder for session request post objects (anonymous, login and device login).
 */
public class SessionRequestBuilder {

	protected SdkData sdkData;
	protected String language, appVersion, tenantId;
	protected boolean remember;
	protected LoginRequest.LoginInfo loginInfo;
	protected LoginRequest.DeviceTypeInfo deviceInfo;

	protected SessionRequestBuilder(SdkData sdkData) {
		this.sdkData = sdkData;
		language = sdkData.getGlobalQueryParamValue(QueryParams.LANG);
		tenantId = sdkData.getGlobalQueryParamValue(QueryParams.TENANT_ID);
		remember = true;
		appVersion = this.sdkData.getAppVersion();
		loginInfo = new LoginRequest.LoginInfo();
		loginInfo.d_id = sdkData.getGlobalQueryParamValue(QueryParams.D_ID);
		loginInfo.d_type = sdkData.getGlobalQueryParamValue(QueryParams.D_TYPE);
		loginInfo.swipe_type = sdkData.getGlobalQueryParamValue(QueryParams.SWIPE_TYPE);
		loginInfo.tenantId = tenantId;

		// drm solution
		switch (sdkData.getDrmSolution()) {
			case NXP: {
				loginInfo.drm = Consts.DRM_TYPE_VMX;
				loginInfo.v_d_id = sdkData.getGlobalQueryParamValue(QueryParams.V_D_ID);
				loginInfo.OMI_type = sdkData.getGlobalQueryParamValue(QueryParams.OMI_TYPE);
				break;
			}
			case CASTLABS_WIDEVINE: {
				loginInfo.drm = Consts.DRM_TYPE_CASTLABS_WIDEVINE;
				loginInfo.v_d_id = sdkData.getGlobalQueryParamValue(QueryParams.CL_D_ID);
				break;
			}
			case CASTLABS_DASH: {
				loginInfo.drm = Consts.DRM_TYPE_CASTLABS_DASH;
				loginInfo.v_d_id = sdkData.getGlobalQueryParamValue(QueryParams.CL_D_ID);
				break;
			}
			case CASTLABS_PLAYREAD: {
				loginInfo.drm = Consts.DRM_TYPE_CASTLABS_PLAYREADY;
				loginInfo.v_d_id = sdkData.getGlobalQueryParamValue(QueryParams.CL_D_ID);
				break;
			}
			case CASTLABS_HLS: {
				loginInfo.drm = Consts.DRM_TYPE_CASTLABS_HLS;
				loginInfo.v_d_id = sdkData.getGlobalQueryParamValue(QueryParams.CL_D_ID);
				break;
			}
			// here add new DRM solutions in future
		}

		deviceInfo = new LoginRequest.DeviceTypeInfo();
		deviceInfo.manufacturer = Build.MANUFACTURER;
		deviceInfo.model = Build.MODEL;
		deviceInfo.release_version = Build.VERSION.RELEASE;
		deviceInfo.build_version = Build.VERSION.INCREMENTAL;
		deviceInfo.product = Build.PRODUCT;
		deviceInfo.id = Build.ID;
		deviceInfo.device_HW = Build.DEVICE;
	}

	/**
	 * Builder that constructs Session request for autologin call
	 * @param sdkData      SdkData with parameters
	 * @param userId       Id of user that has to be logged in
	 * @param oneTimeToken Cached one time token (substitution of password for auto logging in)
	 * @return prepared SessionRequestBuilder which is setup to auto login
	 */
	public static SessionRequestBuilder autoLogin(SdkData sdkData, String userId, String oneTimeToken) {
		SessionRequestBuilder builder = new SessionRequestBuilder(sdkData);
		if (userId != null) builder.loginInfo.u_id = userId;
		builder.loginInfo.oneTimeToken = oneTimeToken;
		return builder;
	}

	/**
	 * Builder that constructs Session request for login call
	 * @param sdkData	SdkData with parameters
	 * @param userId	Id of user that has to be logged in
	 * @param password 	Password of user that has to be logged in
	 * @return prepared SessionRequestBuilder which is setup to login
	 */
	public static SessionRequestBuilder login(SdkData sdkData, String userId, String password) {
		SessionRequestBuilder builder = new SessionRequestBuilder(sdkData);
		if (userId != null) builder.loginInfo.u_id = userId;
		else builder.loginInfo.u_id = sdkData.getGlobalQueryParamValue(QueryParams.U_ID);
		if (password != null) builder.loginInfo.pw = password;
		else builder.loginInfo.pw = sdkData.getGlobalQueryParamValue(QueryParams.PW);
		return builder;
	}

//	public static SessionRequestBuilder socialLogin(GenericInterface api, String socialUserId, String socialAccessToken, String socialNetworkType) {
//		SessionRequestBuilder builder = new SessionRequestBuilder(api);
//		builder.loginInfo.u_id = socialUserId;
//		builder.loginInfo.socialAccountToken = socialAccessToken;
//		builder.loginInfo.social_network_type = socialNetworkType;
//		return builder;
//	}
//
//	public static SessionRequestBuilder anonymous(GenericInterface api) {
//		SessionRequestBuilder builder = new SessionRequestBuilder(api);
//		return builder;
//	}
//
//	public static SessionRequestBuilder deviceLogin(GenericInterface api, String userId, String devicePassword) {
//		SessionRequestBuilder builder = new SessionRequestBuilder(api);
//		if (userId != null) builder.loginInfo.u_id = userId;
//		else builder.loginInfo.u_id = builder.sdkData.getParam(URLCreator.U_ID);
//		builder.loginInfo.d_pw = devicePassword;
//		builder.remember = false;
//		return builder;
//	}
//
//	public AnonymSignonPlayoutRequest buildRequestAnonymous() {
//		AnonymSignonPlayoutRequest request = new AnonymSignonPlayoutRequest();
//		request.app_version = appVersion;
//		request.login_info = loginInfo;
//		request.device_type_info = deviceInfo;
//		request.lang = language;
//		request.tenantId = tenantId;
//		return request;
//	}

	/**
	 * Builds {@link LoginRequest}.
	 * @return login request object
	 */
	public LoginRequest buildRequestLogin() {
		LoginRequest request = new LoginRequest();
		request.app_version = appVersion;
		request.login_info = loginInfo;
		request.device_type_info = deviceInfo;
		request.lang = language;
		return request;
	}

//	public DeviceLoginPlayoutRequest buildRequestDeviceLogin() {
//		DeviceLoginPlayoutRequest request = new DeviceLoginPlayoutRequest();
//		request.app_version = appVersion;
//		request.login_info = loginInfo;
//		request.device_type_info = deviceInfo;
//		request.lang = language;
//		request.tenantId = tenantId;
//		return request;
//	}

	public boolean shouldRemember() {
		return remember;
	}

	public String getUserId() {
		return loginInfo.u_id;
	}

	public String getLanguage() {
		return language;
	}
}
