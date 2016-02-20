package com.coding4people.mosquitoreport.api.factories;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.cloudsearchdomain.AmazonCloudSearchDomain;
import com.amazonaws.services.cloudsearchv2.AmazonCloudSearch;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.coding4people.mosquitoreport.api.Env;

public class FactoryBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bindFactory(AmazonCloudSearchDomainFactory.class).to(AmazonCloudSearchDomain.class);
        bindFactory(AmazonCloudSearchFactory.class).to(AmazonCloudSearch.class);
        bindFactory(AmazonDynamoDBFactory.class).to(AmazonDynamoDB.class);
        bindFactory(DynamoDBConfigurationFactory.class).to(ClientConfiguration.class);
        bindFactory(DynamoDBFactory.class).to(DynamoDB.class);
        bindFactory(DynamoDBMapperFactory.class).to(DynamoDBMapper.class);
        bindFactory(EnvFactory.class).to(Env.class);
    }
}
