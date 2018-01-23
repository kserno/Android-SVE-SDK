package com.deluxe.testapp.di;

import com.deluxe.testapp.connect.ConnectFragment;

import dagger.Component;
import dagger.Subcomponent;

/**
 * Created by filipsollar on 22.1.18.
 */
@Component(dependencies = BaseComponent.class, modules = ConnectModule.class)
public interface ConnectComponent {
    void inject(ConnectFragment fragment);
}
