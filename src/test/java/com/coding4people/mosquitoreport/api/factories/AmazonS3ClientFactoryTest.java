package com.coding4people.mosquitoreport.api.factories;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.amazonaws.services.s3.AmazonS3Client;

public class AmazonS3ClientFactoryTest {

    @Test
    public void testProvide() {
        AmazonS3ClientFactory factory = new AmazonS3ClientFactory();
        AmazonS3Client client = factory.provide();
        
        assertNotNull(client);
        
        factory.dispose(client);
    }
}
