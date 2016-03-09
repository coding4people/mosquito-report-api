package com.coding4people.mosquitoreport.api.factories;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameOverride;
import com.coding4people.mosquitoreport.api.Env;

public class DynamoDBMapperFactory implements Factory<DynamoDBMapper> {
    DynamoDBMapper mapper;

    @Inject
    public DynamoDBMapperFactory(AmazonDynamoDB client, Env env) {
        mapper = new DynamoDBMapper(client,
                new DynamoDBMapperConfig.Builder()
                        .withTableNameOverride(TableNameOverride
                                .withTableNamePrefix(env.get("DYNAMODB_TABLE_PREFIX").orElse("localhost") + "."))
                .build());
    }

    @Override
    public void dispose(DynamoDBMapper mapper) {
    }

    @Override
    public DynamoDBMapper provide() {
        return mapper;
    }
}
