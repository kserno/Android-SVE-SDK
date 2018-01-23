package com.deluxe.svesdk;

/**
 * Data object that bundles all values that persist SDK manager lifecycle.
 */
public class SdkData {


    protected String sessionId = "";
    protected String sessionLanguage = "";
    protected String npsToken = "";
    protected int sessionType;
//    protected LinkedHashMap<Integer,ServiceItem> mServiceCategories;
//    private ArrayList<SwipeDeviceItem> mDeviceList = new ArrayList<SwipeDeviceItem>();
//    protected Profile mSelectedProfile;
//    protected Profile mSelectedProfileParent;
//    private ArrayList<DeviceSlot> mSlotList;
    private int maxSubAccounts = -1;
    protected String appVersion = "";

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionLanguage() {
        return sessionLanguage;
    }

    public void setSessionLanguage(String sessionLanguage) {
        this.sessionLanguage = sessionLanguage;
    }

    public String getNpsToken() {
        return npsToken;
    }

    public void setNpsToken(String npsToken) {
        this.npsToken = npsToken;
    }

    public int getSessionType() {
        return sessionType;
    }

    public void setSessionType(int sessionType) {
        this.sessionType = sessionType;
    }

    public int getMaxSubAccounts() {
        return maxSubAccounts;
    }

    public void setMaxSubAccounts(int maxSubAccounts) {
        this.maxSubAccounts = maxSubAccounts;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }
}
