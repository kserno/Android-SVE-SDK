package com.deluxe.svesdk;

import com.deluxe.svesdk.utils.Consts;

/**
 * Data object that bundles all values that persist SDK manager lifecycle.
 */
public class SdkData {

    protected Consts.DRM_SOLUTION drmSolution = Consts.DRM_SOLUTION.NO_DRM;

    protected String sessionId = "";
    protected String sessionLanguage = "";
    protected String npsToken = "";
    protected int sessionType;
//    protected LinkedHashMap<Integer,ServiceItem> mServiceCategories;
//    private ArrayList<SwipeDeviceItem> mDeviceList = new ArrayList<SwipeDeviceItem>();
//    protected Profile mSelectedProfile;
//    protected Profile mSelectedProfileParent;
//    private ArrayList<DeviceSlot> mSlotList;
    protected int maxSubAccounts = -1;
    protected String appVersion = "";

    /**
     * Returns previously set preferred drm solution. Available options in {@link Consts}.
     * @return Desired drm solution
     */
    public Consts.DRM_SOLUTION getDrmSolution() {
        return drmSolution;
    }

    /**
     * Sets desired drm solution to be used in service calls. Available options in {@link Consts}.
     * @param drmSolution
     */
    public void setDrmSolution(Consts.DRM_SOLUTION drmSolution) {
        this.drmSolution = drmSolution;
    }

    /**
     * Returns current id of a session.
     * @return String - session id
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Sets current id of a session.
     * @param sessionId
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * Returns current language of a session after login.
     * @return String - session id
     */
    public String getSessionLanguage() {
        return sessionLanguage;
    }

    /**
     * Sets current language of a session after login.
     * @param sessionLanguage
     */
    public void setSessionLanguage(String sessionLanguage) {
        this.sessionLanguage = sessionLanguage;
    }

    /**
     * @return Current nps token.
     */
    public String getNpsToken() {
        return npsToken;
    }

    public void setNpsToken(String npsToken) {
        this.npsToken = npsToken;
    }

    /**
     * Returns current type of a session. Available types are EXPIRED=3, LOGGED_IN=2, ANONYMOUS=1 and UNKNOWN=0.
     * @return int - Type of session
     */
    public int getSessionType() {
        return sessionType;
    }

    /**
     * Sets current type of a session. Available types are EXPIRED=3, LOGGED_IN=2, ANONYMOUS=1 and UNKNOWN=0.
     * @param sessionType
     */
    public void setSessionType(int sessionType) {
        this.sessionType = sessionType;
    }

    /**
     * Returns previously set maximum sub accounts, -1 means value not received in login response
     * @return maximum sub accounts
     */
    public int getMaxSubAccounts() {
        return maxSubAccounts;
    }

    /**
     * Sets maximum of sub accounts, -1 means value not received in login response
     * @param maxSubAccounts maximum sub accounts
     */
    public void setMaxSubAccounts(int maxSubAccounts) {
        this.maxSubAccounts = maxSubAccounts;
    }

    /**
     * Returns previously set app version.
     * @return app version
     */
    public String getAppVersion() {
        return appVersion;
    }

    /**
     * Sets app version.
     * @param appVersion app version
     */
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }
}
