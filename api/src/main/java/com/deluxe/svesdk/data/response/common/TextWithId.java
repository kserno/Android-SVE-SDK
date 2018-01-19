package com.deluxe.svesdk.data.response.common;

import com.google.gson.annotations.SerializedName;

/**
 */
public class TextWithId {
    @SerializedName("#text")
    public String text;

    @SerializedName("-id")
    public String id;

    @Override
    public String toString() {
        return " (id = "+id+") text = "+text;
    }
}
