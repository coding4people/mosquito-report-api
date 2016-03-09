package com.coding4people.mosquitoreport.api.auth;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class AuthenticationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(AuthenticationService.class).to(AuthenticationService.class);
    }
}
