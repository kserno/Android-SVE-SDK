package com.deluxe.svesdk;

/**
 * Generic callback for service calls.
 * @param <T> model class that will be retrieved by service call
 */
public interface ApiCallback<T> {

    /** Successful sdk service response. */
    void onResponse(T response);

    /** Invoked when a network or unexpected exception occurred during the HTTP request. */
    void onFailure(Throwable t);

}
