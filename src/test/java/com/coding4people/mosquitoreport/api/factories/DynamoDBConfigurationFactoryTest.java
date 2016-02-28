package com.coding4people.mosquitoreport.api.factories;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.amazonaws.ClientConfiguration;

public class DynamoDBConfigurationFactoryTest {

    @Test
    public void testProvide() {
        DynamoDBConfigurationFactory factory = new DynamoDBConfigurationFactory();
        ClientConfiguration instance = factory.provide();
        
        assertNotNull(instance);
        
        factory.dispose(instance);
    }
}
