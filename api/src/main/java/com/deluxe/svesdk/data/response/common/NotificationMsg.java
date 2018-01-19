package com.deluxe.svesdk.data.response.common;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 */
public class NotificationMsg implements Serializable {

    private String lang = "";

    private Text_Item text_list;

    private Uri_Item uri_list;

    public String getLang() {
        return lang;
    }

    public Text_Item getText_list() {
        return text_list;
    }

    public Uri_Item getUri_list() {
        return uri_list;
    }

    public static class Text_Item {
		public ArrayList<TextWithId> text;
	}

    public static class Uri_Item {
        public ArrayList<TextWithId> uri;

    }

}
