package com.deluxe.sveapi.data.response.common;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Resp raw data object.
 */
public class Resp implements Serializable {

    private String code = null;
    private Params params;
	private String popup_id;
	private Message_Lst message_lst;
	private NotificationMsg notification_msg;

	public String getCode() { return code; }

	public ArrayList<Params.Param> getParams () {
		return params.param;
	}

	public String getPopup_id() {
		return popup_id;
	}

	public Message_Lst getMessage_lst() {
		return message_lst;
	}

	public NotificationMsg getNotifMsg() {
		return notification_msg;
	}

	public static class Message_Lst {
        public ArrayList<Msg_Unit> msg_unit;
    }

    public static class Msg_Unit implements Serializable {
        public String res_key;
        public String id;
        public Param_List param_list;

        public static class Param_List {
            public ArrayList<MsgParam> msgParam;
        }

        public static class MsgParam {
            public String index;
            public String string;
            public String dateTime;
            public String decimal;
            public String integer;
        }
    }

    public String getTextFromID(String id) {
        if (notification_msg != null && notification_msg.getText_list() != null && notification_msg.getText_list().text!=null) {
            for (int i = 0; i < notification_msg.getText_list().text.size(); i++) {
                TextWithId text = notification_msg.getText_list().text.get(i);
                if (text.id.toLowerCase().endsWith(id.toLowerCase())) {
                    return text.text;
                }
            }
        }
        return "";
    }

    public String getUrlFromID(String id) {
        if (notification_msg != null && notification_msg.getUri_list() != null && notification_msg.getUri_list().uri!=null) {
            for (TextWithId uriItem : notification_msg.getUri_list().uri) {
                if (uriItem.id.toLowerCase().endsWith(id.toLowerCase())) {
                    return uriItem.text;
                }
            }
        }
        return "";
    }

    public String getParamValue(String paramName) {
        if (params == null || params.param == null) return null;

        for (Params.Param param : params.param) {
            if (param.pname != null && param.pname.equalsIgnoreCase(paramName)) {
                return param.pval;
            }
        }
        return null;
    }

}
