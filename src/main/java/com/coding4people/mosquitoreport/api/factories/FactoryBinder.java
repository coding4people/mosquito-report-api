package com.coding4people.mosquitoreport.api.factories;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.cloudsearchv2.AmazonCloudSearch;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.s3.AmazonS3Client;
import com.coding4people.mosquitoreport.api.Env;

public class FactoryBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bindFactory(AmazonCloudSearchFactory.class).to(AmazonCloudSearch.class).in(Singleton.class);
        bindFactory(AmazonDynamoDBFactory.class).to(AmazonDynamoDB.class).in(Singleton.class);
        bindFactory(AmazonS3ClientFactory.class).to(AmazonS3Client.class).in(Singleton.class);
        bindFactory(DynamoDBConfigurationFactory.class).to(ClientConfiguration.class).in(Singleton.class);
        bindFactory(DynamoDBFactory.class).to(DynamoDB.class).in(Singleton.class);
        bindFactory(DynamoDBMapperFactory.class).to(DynamoDBMapper.class).in(Singleton.class);
        bindFactory(EnvFactory.class).to(Env.class).in(Singleton.class);
    }
}
