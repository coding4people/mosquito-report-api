package com.coding4people.mosquitoreport.api.factories;

import static org.junit.Assert.assertNotNull;

import java.util.concurrent.ExecutorService;

import org.junit.Test;

public class ExecutorServiceFactoryTest {
    @Test
    public void testProvide() {
        ExecutorServiceFactory factory = new ExecutorServiceFactory();
        ExecutorService executorService = factory.provide();
        
        assertNotNull(executorService);
        
        factory.dispose(executorService);
    }
}
