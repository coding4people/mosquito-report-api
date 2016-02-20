package com.coding4people.mosquitoreport.api.factories;

import static com.amazonaws.PredefinedClientConfigurations.dynamoDefault;

import org.glassfish.hk2.api.Factory;

import com.amazonaws.ClientConfiguration;

public class DynamoDBConfigurationFactory implements Factory<ClientConfiguration> {
    @Override
    public ClientConfiguration provide() {
        return dynamoDefault();
    }

    @Override
    public void dispose(ClientConfiguration instance) {
        
    }
}
