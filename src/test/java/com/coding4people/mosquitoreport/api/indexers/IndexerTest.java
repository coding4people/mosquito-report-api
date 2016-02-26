package com.coding4people.mosquitoreport.api.indexers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.glassfish.hk2.api.MultiException;
import org.junit.Test;
import org.mockito.Mock;

import com.amazonaws.services.cloudsearchdomain.AmazonCloudSearchDomain;
import com.amazonaws.services.cloudsearchv2.AmazonCloudSearch;
import com.amazonaws.services.cloudsearchv2.model.DescribeDomainsResult;
import com.amazonaws.services.cloudsearchv2.model.DomainStatus;
import com.amazonaws.services.cloudsearchv2.model.ServiceEndpoint;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.coding4people.mosquitoreport.api.Env;
import com.coding4people.mosquitoreport.api.WithService;
import com.coding4people.mosquitoreport.api.models.WithGuid;

import jersey.repackaged.com.google.common.collect.Lists;

public class IndexerTest extends WithService {
    @Mock
    AmazonCloudSearch amazonCloudSearch;

    @Mock
    Env env;

    @Mock
    AmazonCloudSearchDomain domain;

    @Test
    public void testThrowsExecptionWhenSearchDomainDoesNotExist() {
        when(env.get("MOSQUITO_REPORT_CLOUDSEARCH_DOMAIN_PREFIX")).thenReturn("test");
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
        when(env.get("MOSQUITO_REPORT_CLOUDSEARCH_DOMAIN_PREFIX")).thenReturn("test");
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

    @DynamoDBTable(tableName = "model")
    public static class Model implements WithGuid {
        private String guid;

        @Override
        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }
    }

    public static class ModelIndexer extends Indexer<Model> {
        @Override
        protected Class<Model> getType() {
            return Model.class;
        }
    }
}
