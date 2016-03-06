package com.coding4people.mosquitoreport.api.factories;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.coding4people.mosquitoreport.api.Env;

public class AmazonDynamoDBFactory implements Factory<AmazonDynamoDB> {
    AmazonDynamoDB client;

    @Inject
    public AmazonDynamoDBFactory(Env env, AWSCredentialsProvider credentialProvider, ClientConfiguration config) {
        client = new AmazonDynamoDBClient(credentialProvider, config);

        String endpoint = env.get("DYNAMODB_ENDPOINT");

        if (endpoint != null) {
            client.setEndpoint(endpoint);
        }
    }

    @Override
    public void dispose(AmazonDynamoDB client) {
    }

    @Override
    public AmazonDynamoDB provide() {
        return client;
    }
}
