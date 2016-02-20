package com.coding4people.mosquitoreport.api;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.mockito.MockitoAnnotations;

abstract public class WithServer extends JerseyTest {
    @Override
    protected ResourceConfig configure() {
        MockitoAnnotations.initMocks(this);

        return Main.commonConfig().register(new MockBinder(this));
    }
}
