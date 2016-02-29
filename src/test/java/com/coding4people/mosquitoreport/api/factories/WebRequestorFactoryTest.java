package com.coding4people.mosquitoreport.api.factories;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.restfb.WebRequestor;

public class WebRequestorFactoryTest {
    @Test
    public void testProvide() {
        WebRequestorFactory factory = new WebRequestorFactory();
        WebRequestor instance = factory.provide();
        
        assertNotNull(instance);
        
        factory.dispose(instance);
    }
}
