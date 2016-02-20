package com.coding4people.mosquitoreport.api.factories;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public class DynamoDBFactory implements Factory<DynamoDB> {
    DynamoDB dynamoDB;
    
    @Inject
    public DynamoDBFactory(AmazonDynamoDB client) {
        dynamoDB = new DynamoDB(client);
    }
    
    @Override
    public void dispose(DynamoDB dynamoDB) {
    }

    @Override
    public DynamoDB provide() {
        return dynamoDB;
    }
}
