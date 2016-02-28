package com.coding4people.mosquitoreport.api.factories;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.amazonaws.AmazonClientException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.retry.PredefinedRetryPolicies;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.coding4people.mosquitoreport.api.Env;

public class AmazonDynamoDBFactoryTest {

    @Test
    public void testAmazonDynamoDBFactoryo() {
        Env env = mock(Env.class);
        ClientConfiguration config = new ClientConfiguration();
        config.setRetryPolicy(PredefinedRetryPolicies.NO_RETRY_POLICY);
        
        when(env.get("MOSQUITO_REPORT_DYNAMODB_ENDPOINT")).thenReturn("fakeendpoint");
        
        AmazonDynamoDB dynamoDB = new AmazonDynamoDBFactory(env, config).provide();
        
        String message = "";
        
        try {
            dynamoDB.listTables();
        } catch (AmazonClientException e) {
            message = e.getMessage();
        }
        
        verify(env).get("MOSQUITO_REPORT_DYNAMODB_ENDPOINT");
        
        assertEquals("Unable to execute HTTP request: fakeendpoint: unknown error", message);
    }
}
