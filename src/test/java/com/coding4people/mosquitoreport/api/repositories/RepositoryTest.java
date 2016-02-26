package com.coding4people.mosquitoreport.api.repositories;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.NotFoundException;

import org.junit.Test;
import org.mockito.Mock;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.coding4people.mosquitoreport.api.WithService;

public class RepositoryTest extends WithService {
    @Mock
    AmazonDynamoDB client;

    @Mock
    DynamoDBMapper mapper;

    @Mock
    DynamoDB dynamoDB;

    @Mock
    Table table;

    @Test
    public void testSave() {
        CreateTableRequest request = new CreateTableRequest();

        when(mapper.generateCreateTableRequest(Model.class)).thenReturn(request);
        when(dynamoDB.createTable(request)).thenReturn(table);

        ModelRepository repository = getService(ModelRepository.class);

        Model model = new Model();

        repository.save(model);

        verify(mapper).save(model);
    }

    @Test
    public void testLoadByHash() {
        CreateTableRequest request = new CreateTableRequest();

        when(mapper.generateCreateTableRequest(Model.class)).thenReturn(request);
        when(dynamoDB.createTable(request)).thenReturn(table);

        ModelRepository repository = getService(ModelRepository.class);

        repository.load("hash");

        verify(mapper).load(Model.class, "hash");
    }

    @Test
    public void testLoadByHashAndRange() {
        CreateTableRequest request = new CreateTableRequest();

        when(mapper.generateCreateTableRequest(Model.class)).thenReturn(request);
        when(dynamoDB.createTable(request)).thenReturn(table);

        ModelRepository repository = getService(ModelRepository.class);

        repository.load("hash", "range");

        verify(mapper).load(Model.class, "hash", "range");
    }

    @Test(expected = NotFoundException.class)
    public void testLoadOrNotFound() {
        CreateTableRequest request = new CreateTableRequest();

        when(mapper.generateCreateTableRequest(Model.class)).thenReturn(request);
        when(dynamoDB.createTable(request)).thenReturn(table);

        ModelRepository repository = getService(ModelRepository.class);

        repository.loadOrNotFound("hash");
    }
    
    @Test
    public void testLoadOrNotFound200() {
        CreateTableRequest request = new CreateTableRequest();
        Model model = new Model();

        when(mapper.generateCreateTableRequest(Model.class)).thenReturn(request);
        when(dynamoDB.createTable(request)).thenReturn(table);
        when(mapper.load(Model.class, "hash")).thenReturn(model);

        ModelRepository repository = getService(ModelRepository.class);

        assertEquals(model, repository.loadOrNotFound("hash"));
        
        verify(mapper).load(Model.class, "hash");
    }
    
    @Test
    public void testResourceInUseException() {
        CreateTableRequest request = new CreateTableRequest();

        when(mapper.generateCreateTableRequest(Model.class)).thenReturn(request);
        when(dynamoDB.createTable(request)).thenThrow(new ResourceInUseException(""));

        getService(ModelRepository.class);
    }
    
    @Test(expected = RuntimeException.class)
    public void testInterruptedException() {
        CreateTableRequest request = new CreateTableRequest();

        when(mapper.generateCreateTableRequest(Model.class)).thenReturn(request);
        when(dynamoDB.createTable(request)).thenThrow(new InterruptedException(""));

        getService(ModelRepository.class);
    }

    public static class Model {
    }

    public static class ModelRepository extends Repository<Model> {
        @Override
        protected Class<Model> getType() {
            return Model.class;
        }
    }
}
