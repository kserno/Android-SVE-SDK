package com.deluxe.svesdk.utils;

/**
 * Constat values used across SDK.
 */
public class Consts {

    /**
     * Drm solution options.
     */
    public enum DRM_SOLUTION
    {
        NXP,
        CASTLABS_WIDEVINE,
        CASTLABS_PLAYREAD,
        CASTLABS_DASH,
        CASTLABS_HLS,
        NO_DRM
    }

    public static final String DRM_TYPE_VMX = "VMX_HLS";
    public static final String DRM_TYPE_CASTLABS_WIDEVINE = "CL_WV";
    public static final String DRM_TYPE_CASTLABS_PLAYREADY = "CL_PR";
    public static final String DRM_TYPE_CASTLABS_DASH = "CL_DASH";
    public static final String DRM_TYPE_CASTLABS_HLS = "CL_HLS";

    public final static int EXPIRED 	= 3;
    public final static int LOGGED_IN 	= 2;
    public final static int ANONYMOUS 	= 1;
    public final static int UNKNOWN 	= 0;

    public static final String FAVORITES_PL_TYPE = "favorites";
    public static final String FAVORITES_PL_NAME = "Favorites";
    public static final String WHITELIST_PL_TYPE = "whitelist";
    public static final String WHITELIST_PL_NAME = "Whitelist";
    public static final String BLACKLIST_PL_TYPE = "blacklist";
    public static final String BLACKLIST_PL_NAME = "Blacklist";

    public static final String PLAYLIST_PL_NAME	= "Playlist";
    public static final String PLAYLIST_PL_TYPE	= "default";
    public static final String HISTORY_PL_NAME	= "History";
    public static final String HISTORY_PL_TYPE	= "history";

    public static final String ASSET_TYPE_VOD		= "vod";
    public static final String ASSET_TYPE_EPISODE	= "episode";
    public static final String ASSET_TYPE_SHOW		= "show";
    public static final String ASSET_TYPE_SEASON	= "season";
    public static final String ASSET_TYPE_CATCHUP	= "catchup";
    public static final String ASSET_TYPE_CHANNEL	= "channel";
    public static final String ASSET_TYPE_CHANNEL_2	= "tv_channel";
    public static final String ASSET_TYPE_MUSIC		= "musicvod";
    public static final String ASSET_TYPE_PLAY		= "play";
    public static final String ASSET_TYPE_TVCHANNEL	= "tv";
    public static final String ASSET_TYPE_MOVIE		= "movie";

    public static final String PAY_TYPE_ALA_CARTE = "ala";
    public static final String PAY_TYPE_WILD_CARD = "wild";

}
