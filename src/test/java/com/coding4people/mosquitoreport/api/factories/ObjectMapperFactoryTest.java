package com.coding4people.mosquitoreport.api.factories;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperFactoryTest {
    @Test
    public void testProvide() {
        ObjectMapperFactory factory = new ObjectMapperFactory();
        ObjectMapper objectMapper = factory.provide();
        
        assertNotNull(objectMapper);
        
        factory.dispose(objectMapper);
    }
}
