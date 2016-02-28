package com.coding4people.mosquitoreport.api.factories;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.amazonaws.AmazonClientException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.retry.PredefinedRetryPolicies;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.coding4people.mosquitoreport.api.Env;

public class AmazonDynamoDBFactoryTest {

    @Test
    public void testProvide() {
        Env env = mock(Env.class);
        ClientConfiguration config = new ClientConfiguration();
        config.setRetryPolicy(PredefinedRetryPolicies.NO_RETRY_POLICY);

        when(env.get("MOSQUITO_REPORT_DYNAMODB_ENDPOINT")).thenReturn("fakeendpoint");

        AWSCredentialsProvider credentialsProvider = new StaticCredentialsProvider(
                new BasicAWSCredentials("fake_access_key_id", "fake_secret_access_key"));

        AmazonDynamoDBFactory factory = new AmazonDynamoDBFactory(env, credentialsProvider, config);
        AmazonDynamoDB dynamoDB = factory.provide();

        String message = "";

        try {
            dynamoDB.listTables();
        } catch (AmazonClientException e) {
            message = e.getMessage();
        }

        verify(env).get("MOSQUITO_REPORT_DYNAMODB_ENDPOINT");

        assertEquals("Unable to execute HTTP request: fakeendpoint: unknown error", message);
        
        factory.dispose(dynamoDB);
    }
}
