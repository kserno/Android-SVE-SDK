package com.deluxe.testapp;

import java.util.List;

/**
 * Created by filipsollar on 22.1.18.
 */

public interface Router {

    void goTo(BaseFragment fragment);
    void replace(BaseFragment fragment);
    void replaceAllWith(List<BaseFragment> fragments);
    void reset(BaseFragment fragment);
    void goBack();

}
