package com.coding4people.mosquitoreport.api.factories;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.coding4people.mosquitoreport.api.Env;

public class AmazonDynamoDBFactory implements Factory<AmazonDynamoDB> {
    AmazonDynamoDB client;

    @Inject
    public AmazonDynamoDBFactory(Env env, ClientConfiguration config) {
        client = new AmazonDynamoDBClient(new DefaultAWSCredentialsProviderChain(), config);

        String endpoint = env.get("MOSQUITO_REPORT_DYNAMODB_ENDPOINT");

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
