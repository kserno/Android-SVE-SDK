package com.deluxe.testapp;

/**
 * Created by filipsollar on 22.1.18.
 */

public class Backend {
    private String mBackend;
    private String mLabel;
    private String mPlayoutUrl;
    private String mNPSUrl;

    public Backend() {
    }

    public Backend(String backend, String label, String playoutUrl, String NPSUrl) {
        mBackend = backend;
        mLabel = label;
        mPlayoutUrl = playoutUrl;
        mNPSUrl = NPSUrl;
    }

    public String getBackend() {
        return mBackend;
    }

    public String getPlayoutUrl() {
        return mPlayoutUrl;
    }

    public String getNPSUrl() {
        return mNPSUrl;
    }

    public String getLabel() {
        return mLabel;
    }
}
