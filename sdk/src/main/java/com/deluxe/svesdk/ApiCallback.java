package com.deluxe.svesdk;

public interface ApiCallback<T> {

    /** Successful sdk service response. */
    void onResponse(T response);

    /** Invoked when a network or unexpected exception occurred during the HTTP request. */
    void onFailure(Throwable t);

}
