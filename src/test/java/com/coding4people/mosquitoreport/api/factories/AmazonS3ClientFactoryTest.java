package com.coding4people.mosquitoreport.api.factories;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class AmazonS3ClientFactoryTest {

    @Test
    public void testProvide() {
        assertNotNull(new AmazonS3ClientFactory().provide());
    }
}
