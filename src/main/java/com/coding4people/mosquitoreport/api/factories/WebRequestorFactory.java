package com.coding4people.mosquitoreport.api.factories;

import org.glassfish.hk2.api.Factory;

import com.restfb.DefaultWebRequestor;
import com.restfb.WebRequestor;

public class WebRequestorFactory implements Factory<WebRequestor> {
    @Override
    public WebRequestor provide() {
        return new DefaultWebRequestor();
    }

    @Override
    public void dispose(WebRequestor instance) {
    }
}
