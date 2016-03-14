package com.coding4people.mosquitoreport.api.indexers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import org.glassfish.hk2.api.MultiException;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import com.amazonaws.services.cloudsearchdomain.AmazonCloudSearchDomain;
import com.amazonaws.services.cloudsearchdomain.AmazonCloudSearchDomainClient;
import com.amazonaws.services.cloudsearchdomain.model.Hit;
import com.amazonaws.services.cloudsearchdomain.model.Hits;
import com.amazonaws.services.cloudsearchdomain.model.SearchRequest;
import com.amazonaws.services.cloudsearchdomain.model.SearchResult;
import com.amazonaws.services.cloudsearchdomain.model.UploadDocumentsRequest;
import com.amazonaws.services.cloudsearchv2.AmazonCloudSearch;
import com.amazonaws.services.cloudsearchv2.model.DescribeDomainsResult;
import com.amazonaws.services.cloudsearchv2.model.DomainStatus;
import com.amazonaws.services.cloudsearchv2.model.ServiceEndpoint;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.coding4people.mosquitoreport.api.Env;
import com.coding4people.mosquitoreport.api.WithService;
import com.coding4people.mosquitoreport.api.models.Searchable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jersey.repackaged.com.google.common.collect.Lists;
import jersey.repackaged.com.google.common.collect.Maps;

public class IndexerTest extends WithService {
    @Mock
    AmazonCloudSearch amazonCloudSearch;

    @Mock
    AmazonCloudSearchDomainClient domain;
    
    @Mock
    ExecutorService executor;
    
    @Mock
    ObjectMapper objectMapper;
    
    Env env = new Env().register("CLOUDSEARCH_DOMAIN_PREFIX", "test");
    
    @Override
    public Env getEnv() {
        return env;
    }

    @Test
    public void testThrowsExecptionWhenSearchDomainDoesNotExist() {
        when(amazonCloudSearch.describeDomains(any()))
                .thenReturn(new DescribeDomainsResult().withDomainStatusList(Lists.newArrayList()));

        try {
            // TODO suppress exception stacktrace
            getService(ModelIndexer.class);
        } catch (MultiException e) {
            assertEquals("Could not find CloudSearch domain: test-model", e.getCause().getMessage());

            return;
        }

        fail("Was expection an exception");
    }

    @Test
    public void testThrowsExecptionWhenSearchServiceDoesNotExist() {
        when(amazonCloudSearch.describeDomains(any())).thenReturn(new DescribeDomainsResult()
                .withDomainStatusList(Lists.newArrayList(new DomainStatus().withSearchService(new ServiceEndpoint()))));

        try {
            // TODO suppress exception stacktrace
            getService(ModelIndexer.class);
        } catch (MultiException e) {
            assertEquals("Could not find SearchService for: test-model", e.getCause().getMessage());

            return;
        }

        fail("Was expection an exception");
    }
    
    @Test
    public void testSearchCenter() {
        when(amazonCloudSearch.describeDomains(any())).thenReturn(new DescribeDomainsResult()
                .withDomainStatusList(Lists.newArrayList(new DomainStatus().withSearchService(new ServiceEndpoint().withEndpoint("http://localhost")))));

        HashMap<String, List<String>> map = Maps.newHashMap();
        map.put("property", Lists.newArrayList("value"));
        SearchResult expected = new SearchResult().withHits(new Hits().withHit(new Hit().withFields(map)));
        
        ArgumentCaptor<SearchRequest> requestCaptor = ArgumentCaptor.forClass(SearchRequest.class);
        
        when(domain.search(requestCaptor.capture())).thenReturn(expected);
        
        List<ObjectNode> result = getService(ModelIndexer.class).searchCenter("0,0");
        
        SearchRequest request = requestCaptor.getValue();
        
        assertEquals("value", result.get(0).get("property").asText());
        assertEquals("latlon:['0.1,-0.1','-0.1,0.1']", request.getQuery());
        assertEquals("{\"distance\":\"haversin(0.0,0.0,latlon.latitude,latlon.longitude)\"}", request.getExpr());
        assertEquals("distance asc", request.getSort());
        assertEquals(Long.valueOf(30L), request.getSize());
    }
    
    @Test
    public void testCreateDomain() {
        when(amazonCloudSearch.describeDomains(any())).thenReturn(new DescribeDomainsResult()
                .withDomainStatusList(Lists.newArrayList(new DomainStatus().withSearchService(new ServiceEndpoint().withEndpoint("http://localhost")))));
        
        AmazonCloudSearchDomain domain = getService(ModelIndexer2.class).createDomain();
        assertNotNull(domain);
        assertTrue(domain instanceof AmazonCloudSearchDomain);
    }
    
    @Test
    public void testIndex() throws Exception {
        when(amazonCloudSearch.describeDomains(any())).thenReturn(new DescribeDomainsResult()
                .withDomainStatusList(Lists.newArrayList(new DomainStatus().withSearchService(new ServiceEndpoint().withEndpoint("http://localhost")))));
        
        objectMapper = new ObjectMapper();
        
        Model model = new Model();
        model.setGuid("00000000-0000-0000-0000-000000000000");
        
        ArgumentCaptor<Runnable> runnableCaptor = ArgumentCaptor.forClass(Runnable.class);
        
        getService(ModelIndexer.class).index(model);
        
        verify(executor).submit(runnableCaptor.capture());
        
        runnableCaptor.getValue().run();
        
        ArgumentCaptor<UploadDocumentsRequest> requestCaptor = ArgumentCaptor.forClass(UploadDocumentsRequest.class);
        
        verify(domain).uploadDocuments(requestCaptor.capture());
        
        Scanner scanner = new Scanner(requestCaptor.getValue().getDocuments());
        
        JsonNode json = new ObjectMapper().readTree(scanner.useDelimiter("\\A").next()).get(0);
        
        assertEquals("add", json.get("type").asText());
        assertEquals("00000000-0000-0000-0000-000000000000", json.get("id").asText());
        assertEquals("00000000-0000-0000-0000-000000000000", json.get("fields").get("guid").asText());
        
        scanner.close();
    }
    
    @Test
    public void testIndexJsonProcessingException() throws Exception {
        when(amazonCloudSearch.describeDomains(any())).thenReturn(new DescribeDomainsResult()
                .withDomainStatusList(Lists.newArrayList(new DomainStatus().withSearchService(new ServiceEndpoint().withEndpoint("http://localhost")))));
        
        Model model = new Model();
        model.setGuid("00000000-0000-0000-0000-000000000000");
        
        ArgumentCaptor<Runnable> runnableCaptor = ArgumentCaptor.forClass(Runnable.class);
        
        getService(ModelIndexer.class).index(Lists.newArrayList(model));
        
        verify(executor).submit(runnableCaptor.capture());
        
        when(objectMapper.writeValueAsString(any())).thenThrow(new StubJsonProcessingException());
        
        runnableCaptor.getValue().run();
        
        verify(domain, never()).uploadDocuments(any());
    }
    
    public static class StubJsonProcessingException extends JsonProcessingException {
        private static final long serialVersionUID = -3023763421872884564L;

        public StubJsonProcessingException() {
            super("");
        }
    }

    @DynamoDBTable(tableName = "model")
    public static class Model implements Searchable {
        private String guid;

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }
        
        @Override
        public String getSearchId() {
            return guid;
        }
    }

    public static class ModelIndexer extends Indexer<Model> {
        @Inject
        AmazonCloudSearchDomainClient domainMock;
        
        @Override
        protected Class<Model> getType() {
            return Model.class;
        }
        
        @Override
        protected AmazonCloudSearchDomain createDomain() {
            return domainMock;
        }
    }
    
    public static class ModelIndexer2 extends Indexer<Model> {
        @Inject
        AmazonCloudSearchDomainClient domainMock;
        
        @Override
        protected Class<Model> getType() {
            return Model.class;
        }
    }
}
