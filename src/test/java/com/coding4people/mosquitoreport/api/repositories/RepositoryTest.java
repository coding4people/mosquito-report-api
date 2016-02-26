package com.coding4people.mosquitoreport.api.repositories;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mock;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
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
    
    public static class Model {}
    
    public static class ModelRepository extends Repository<Model> {
        @Override
        protected Class<Model> getType() {
            return Model.class;
        }
    }
}
