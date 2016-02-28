package com.coding4people.mosquitoreport.api.factories;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.amazonaws.services.cloudsearchv2.AmazonCloudSearch;

public class AmazonCloudSearchFactoryTest {

    @Test
    public void testProvide() {
        AmazonCloudSearchFactory factory = new AmazonCloudSearchFactory();
        AmazonCloudSearch client = factory.provide();
        
        assertNotNull(client);
        
        factory.dispose(client);
    }
}
