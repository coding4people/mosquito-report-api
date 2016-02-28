package com.coding4people.mosquitoreport.api.factories;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class DynamoDBConfigurationFactoryTest {

    @Test
    public void testAmazonCloudSearchFactory() {
        assertNotNull(new DynamoDBConfigurationFactory().provide());
    }
}
