package com.deluxe.testapp;

import com.deluxe.svesdk.SdkManager;
import com.deluxe.testapp.connect.ConnectPresenter;
import com.deluxe.testapp.connect.ConnectScreen;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Created by filipsollar on 23.1.18.
 */
@RunWith(JUnit4.class)
public class ConnectPresenterTest {

    @Mock ConnectScreen mConnectScreen;
    @Mock SdkManager mSdkManager;
    private ConnectPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mPresenter = new ConnectPresenter(mSdkManager);
    }

    @Test
    public void shouldOpenBackendSelectionDialogOnSelectBackendClicked() throws Exception {
        mPresenter.onSelectBackendClicked(mConnectScreen);
        Mockito.verify(mConnectScreen).showBackendSelectionDialog(ArgumentMatchers.any(CharSequence[].class));
    }

    @Test
    public void shouldOpenTenantIdSelectionDialogOnTenantIdClicked() throws Exception {
        mPresenter.onSelectTenantIdClicked(mConnectScreen);
        Mockito.verify(mConnectScreen).showTenantIdSelectionDialog();
    }

    @Test
    public void shouldOpenDeviceTypeSelectionDialogOnDeviceTypeClicked() throws Exception {
        mPresenter.onSelectDeviceTypeClicked(mConnectScreen);
        Mockito.verify(mConnectScreen).showDeviceTypeSelectionDialog();
    }

    @Test
    public void shouldSetBackendOnBackendSelected() throws Exception {
        mPresenter.onBackendSelected(mConnectScreen, 0);
        Mockito.verify(mConnectScreen).setBackend(ArgumentMatchers.any(Backend.class));
    }


}
