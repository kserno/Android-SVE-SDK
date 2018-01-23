package com.deluxe.testapp.connect;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.deluxe.testapp.Backend;
import com.deluxe.testapp.BaseApplication;
import com.deluxe.testapp.BaseFragment;
import com.deluxe.testapp.NextFragment;
import com.deluxe.testapp.R;
import com.deluxe.testapp.Router;
import com.deluxe.testapp.di.ConnectComponent;
import com.deluxe.testapp.di.ConnectModule;
import com.deluxe.testapp.di.DaggerConnectComponent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by filipsollar on 22.1.18.
 */

public class ConnectFragment extends BaseFragment implements ConnectScreen{

    private static final String TAG = ConnectFragment.class.getSimpleName();

    @Inject ConnectPresenter mPresenter;
    @Inject Router mRouter;

    @BindView(R.id.et_backend) EditText mEtBackend;
    @BindView(R.id.et_nps_url) EditText mEtNpsUrl;
    @BindView(R.id.et_playout_url) EditText mEtPlayoutUrl;
    @BindView(R.id.et_device_id) EditText mEtDeviceId;
    @BindView(R.id.et_device_type) EditText mEtDeviceType;
    @BindView(R.id.et_tenant_id) EditText mEtTenantId;

    @BindView(R.id.progress_bar) ProgressBar mProgressBar;

    private ConnectComponent mComponent;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getComponent().inject(this);

        initializeViews();
        mPresenter.onCreate(this);
    }

    private void initializeViews() {
        mEtBackend.setClickable(false); // to prevent opening keyboard
        mEtBackend.setFocusable(false);

        mEtTenantId.setClickable(false);
        mEtTenantId.setFocusable(false);

        mEtDeviceType.setClickable(false);
        mEtDeviceType.setFocusable(false);

    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_connect;
    }

    @OnClick({R.id.text_input_layout_backend, R.id.et_backend})
    public void onBackendClicked() {
        mPresenter.onSelectBackendClicked(this);
    }

    @OnClick({R.id.text_input_layout_device_type, R.id.et_device_type})
    public void onDeviceTypeClicked() {
        mPresenter.onSelectDeviceTypeClicked(this);
    }

    @OnClick({R.id.text_input_layout_tenant_id, R.id.et_tenant_id})
    public void onTenantIdClicked() {
        mPresenter.onSelectTenantIdClicked(this);
    }

    @OnClick(R.id.bt_reset)
    public void onResetClicked() {
        mPresenter.onResetClicked(this);
    }

    @OnClick(R.id.bt_connect)
    public void onConnectClicked() {
        mPresenter.connect(this);
    }

    @Override
    public void showBackendSelectionDialog(CharSequence[] items) {

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.onBackendSelected(ConnectFragment.this, which);
            }
        };

        new AlertDialog.Builder(getContext())
                .setTitle(R.string.title_select_backend)
                .setItems(items, listener)
                .show();
    }

    @Override
    public void showTenantIdSelectionDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.title_select_tenant_id)
                .setItems(R.array.tenant_ids, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mEtTenantId.setText(getResources().getStringArray(R.array.tenant_ids)[which]);
                    }
                })
                .show();
    }

    @Override
    public void showDeviceTypeSelectionDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.title_select_device_type)
                .setItems(R.array.device_types, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mEtDeviceType.setText(getResources().getStringArray(R.array.device_types)[which]);
                    }
                })
                .show();
    }

    @Override
    public void setBackend(Backend backend) {
        mEtBackend.setText(backend.getLabel());
        mEtNpsUrl.setText(backend.getNPSUrl());
        mEtPlayoutUrl.setText(backend.getPlayoutUrl());
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void reset() {
        mPresenter.onCreate(this);
        mEtTenantId.setText(null);
        mEtDeviceType.setText(null);
        mEtDeviceId.setText(null);

        mEtPlayoutUrl.requestFocus();
    }

    @Override
    public void applicationOutdated() {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.title_version_outdated)
                .setMessage(R.string.error_version_outdated)
                .show();
    }

    @Override
    public void success() {
        mRouter.reset(new NextFragment());
    }

    private ConnectComponent getComponent() {
        if (mComponent == null) {
            mComponent = DaggerConnectComponent.builder().baseComponent(
                    ((BaseApplication) getActivity().getApplication()).getBaseComponent())
                    .connectModule(new ConnectModule((Router) getActivity()))
                    .build();
        }
        return mComponent;
    }

}
