package com.coding4people.mosquitoreport.api.repositories;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;

abstract public class Repository<T> {
    @Inject AmazonDynamoDB client;
    
    @Inject DynamoDBMapper mapper;
    
    @Inject DynamoDB dynamoDB;

    abstract protected Class<T> getType();

    @PostConstruct
    protected void postConstruct() {
        createTable();
        postCreateTable();
    }
    
    protected void postCreateTable() {
    }
    
    protected void createTable() {
        try {
            CreateTableRequest ctr = mapper.generateCreateTableRequest(getType()).withProvisionedThroughput(
                    new ProvisionedThroughput().withReadCapacityUnits(1L).withWriteCapacityUnits(1L));

            dynamoDB.createTable(ctr).waitForActive();
        } catch (ResourceInUseException e) {
            // Table already exists
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public T load(String hash) {
        return mapper.load(getType(), hash);
    }
    
    public T loadOrNotFound(String hash) {
        T t = load(hash);
        
        if (t == null) {
            throw new NotFoundException();
        }
        
        return t;
    }
    
    public void save(T o) {
        mapper.save(o);
    }
}
