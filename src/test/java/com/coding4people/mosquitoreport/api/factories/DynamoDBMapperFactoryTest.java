package com.coding4people.mosquitoreport.api.factories;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.coding4people.mosquitoreport.api.Env;

public class DynamoDBMapperFactoryTest {

    @Test
    public void testAmazonCloudSearchFactory() {
        AmazonDynamoDB amazonDynamoDB = mock(AmazonDynamoDB.class);
        Env env = mock(Env.class);
        
        assertNotNull(new DynamoDBMapperFactory(amazonDynamoDB, env).provide());
        
        verify(env).get("MOSQUITO_REPORT_DYNAMODB_TABLE_PREFIX");
    }
}
