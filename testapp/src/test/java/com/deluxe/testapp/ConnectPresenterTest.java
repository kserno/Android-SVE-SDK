package com.deluxe.testapp;

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
    private ConnectPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mPresenter = new ConnectPresenter();
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
}
