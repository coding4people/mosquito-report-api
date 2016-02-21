package com.coding4people.mosquitoreport.api.factories;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.InternalServerErrorException;

import org.glassfish.hk2.api.Factory;

import com.amazonaws.services.cloudsearchdomain.AmazonCloudSearchDomain;
import com.amazonaws.services.cloudsearchdomain.AmazonCloudSearchDomainClient;
import com.amazonaws.services.cloudsearchv2.AmazonCloudSearch;
import com.amazonaws.services.cloudsearchv2.model.DescribeDomainsRequest;
import com.amazonaws.services.cloudsearchv2.model.DomainStatus;

@Singleton
public class AmazonCloudSearchDomainFactory implements Factory<AmazonCloudSearchDomain> {
    AmazonCloudSearchDomain client;

    @Inject
    public AmazonCloudSearchDomainFactory(AmazonCloudSearch amazonCloudSearch) {
        DescribeDomainsRequest describeDomainsRequest = new DescribeDomainsRequest().withDomainNames("focus");
        List<DomainStatus> list = amazonCloudSearch.describeDomains(describeDomainsRequest).getDomainStatusList();

        if (list.isEmpty()) {
            throw new InternalServerErrorException("Could not find CloudSearch domain");
        }

        client = new AmazonCloudSearchDomainClient();
        client.setEndpoint(list.get(0).getSearchService().getEndpoint());
    }

    @Override
    public void dispose(AmazonCloudSearchDomain client) {
    }

    @Override
    public AmazonCloudSearchDomain provide() {
        return client;
    }
}
