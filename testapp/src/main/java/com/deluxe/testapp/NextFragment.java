package com.deluxe.testapp;

import com.deluxe.testapp.connect.ConnectFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by filipsollar on 22.1.18.
 */

public class NextFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_next;
    }

    @OnClick(R.id.bt_change_configuration)
    public void changeConfiguration() {
        if (getActivity() instanceof Router) {
            ((Router) getActivity()).reset(new ConnectFragment());
        }
    }

}
