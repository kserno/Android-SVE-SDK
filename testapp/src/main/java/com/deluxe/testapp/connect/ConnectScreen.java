package com.deluxe.testapp.connect;

import com.deluxe.testapp.Backend;

import java.util.List;


/**
 * Created by filipsollar on 22.1.18.
 */

public interface ConnectScreen {

    void showBackendSelectionDialog(CharSequence[] backends);
    void showTenantIdSelectionDialog();
    void showDeviceTypeSelectionDialog();

    void setBackend(Backend backend);
    void showLoading();
    void hideLoading();

    void reset();
    void applicationOutdated();
    void success();
}
