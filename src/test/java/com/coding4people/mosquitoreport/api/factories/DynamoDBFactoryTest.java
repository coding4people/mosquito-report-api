package com.coding4people.mosquitoreport.api.factories;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;

public class DynamoDBFactoryTest {

    @Test
    public void testProvide() {
        AmazonDynamoDB amazonDynamoDB = mock(AmazonDynamoDB.class);
        assertNotNull(new DynamoDBFactory(amazonDynamoDB).provide());
    }
}
