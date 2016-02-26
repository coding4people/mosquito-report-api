package com.coding4people.mosquitoreport.api;

import org.glassfish.jersey.server.ApplicationHandler;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Before;
import org.mockito.MockitoAnnotations;

abstract public class WithService implements BaseTest {
   
    
    @Before
    public void initialize() {
        MockitoAnnotations.initMocks(this);
    }

    protected ResourceConfig configure() {
        return Main.commonConfig().register(new MockBinder(this));
    }
    
    protected <T> T getService(Class<T> clazz) {
        return new ApplicationHandler(configure().register(new GenericBinder<T>() {
            public Class<T> getType() {
                return clazz;
            }
        })).getServiceLocator().getService(clazz);
    }
}
