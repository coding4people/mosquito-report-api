package com.coding4people.mosquitoreport.api.factories;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.amazonaws.auth.AWSCredentialsProvider;

public class AWSCredentialsProviderFactoryTest {

    @Test
    public void testProvide() {
        AWSCredentialsProviderFactory factory = new AWSCredentialsProviderFactory();
        AWSCredentialsProvider provider = factory.provide();
        
        assertNotNull(provider);
        
        factory.dispose(provider);
    }
}
