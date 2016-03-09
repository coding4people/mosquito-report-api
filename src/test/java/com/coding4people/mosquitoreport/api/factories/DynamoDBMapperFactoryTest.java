package com.coding4people.mosquitoreport.api.factories;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.coding4people.mosquitoreport.api.Env;

public class DynamoDBMapperFactoryTest {

    @Test
    public void testProvide() {
        AmazonDynamoDB amazonDynamoDB = mock(AmazonDynamoDB.class);
        Env env = mock(Env.class);
        when(env.get("DYNAMODB_TABLE_PREFIX")).thenReturn(Optional.of("table"));
        DynamoDBMapperFactory factory = new DynamoDBMapperFactory(amazonDynamoDB, env);
        DynamoDBMapper mapper = factory.provide();
        
        assertNotNull(mapper);
        
        factory.dispose(mapper);
        
        verify(env).get("DYNAMODB_TABLE_PREFIX");
    }
}
