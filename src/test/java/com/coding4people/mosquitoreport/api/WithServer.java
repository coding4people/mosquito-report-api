package com.coding4people.mosquitoreport.api;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.mockito.MockitoAnnotations;

abstract public class WithServer extends JerseyTest implements BaseTest {
    @Override
    protected ResourceConfig configure() {
        MockitoAnnotations.initMocks(this);

        return  new Config().configureFramework().register(new MockBinder(this));
    }
}
