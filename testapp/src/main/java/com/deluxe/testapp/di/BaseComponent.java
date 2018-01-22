package com.deluxe.testapp.di;

import com.deluxe.testapp.BaseApplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by filipsollar on 22.1.18.
 */
@Component(modules = BaseModule.class)
public interface BaseComponent {
    void inject(BaseApplication application);
}
