package com.deluxe.testapp;

/**
 * Created by filipsollar on 22.1.18.
 */

public class Backend {
    private String mBackend;
    private String mLabel;
    private String mPlayoutUrl;
    private String mNpsUrl;
    private String mMcsUrl;

    public Backend() {
    }

    public Backend(String backend, String label, String playoutUrl, String npsUrl) {
        mBackend = backend;
        mLabel = label;
        mPlayoutUrl = playoutUrl;
        mNpsUrl = npsUrl;
        mMcsUrl = npsUrl;
    }

    public String getBackend() {
        return mBackend;
    }

    public String getPlayoutUrl() {
        return mPlayoutUrl;
    }

    public String getNpsUrl() {
        return mNpsUrl;
    }

    public String getLabel() {
        return mLabel;
    }

    public String getMcsUrl() {
        return mMcsUrl;
    }
}
