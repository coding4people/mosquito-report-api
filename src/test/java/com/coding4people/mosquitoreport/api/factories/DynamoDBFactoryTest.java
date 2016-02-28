package com.coding4people.mosquitoreport.api.factories;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public class DynamoDBFactoryTest {

    @Test
    public void testProvide() {
        AmazonDynamoDB amazonDynamoDB = mock(AmazonDynamoDB.class);
        DynamoDBFactory factory = new DynamoDBFactory(amazonDynamoDB);
        DynamoDB dynamoDB = factory.provide();
        
        assertNotNull(dynamoDB);
        
        factory.dispose(dynamoDB);
    }
}
