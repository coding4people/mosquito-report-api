package com.coding4people.mosquitoreport.api.factories;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.coding4people.mosquitoreport.api.Env;

public class HttpServerFactoryTest {
    @Test
    public void testProvide() {
        Env.instance.register("PORT", "9876");
        
        HttpServerFactory factory = new HttpServerFactory();
        
        assertNotNull(factory.provide());
    }
}
