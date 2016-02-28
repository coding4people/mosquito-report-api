package com.coding4people.mosquitoreport.api.factories;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.coding4people.mosquitoreport.api.Env;

public class EnvFactoryTest {

    @Test
    public void testProvide() {
        EnvFactory factory = new EnvFactory();
        Env env = factory.provide();
        
        assertNotNull(env);
        
        factory.dispose(env);
    }
}
