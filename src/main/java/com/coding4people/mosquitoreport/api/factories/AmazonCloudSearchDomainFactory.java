package com.coding4people.mosquitoreport.api.factories;

import org.glassfish.hk2.api.Factory;

import com.amazonaws.services.cloudsearchdomain.AmazonCloudSearchDomain;
import com.amazonaws.services.cloudsearchdomain.AmazonCloudSearchDomainClient;

public class AmazonCloudSearchDomainFactory implements Factory<AmazonCloudSearchDomain> {
    AmazonCloudSearchDomain client;

    public AmazonCloudSearchDomainFactory() {
        client = new AmazonCloudSearchDomainClient();
    }

    @Override
    public void dispose(AmazonCloudSearchDomain client) {
    }

    @Override
    public AmazonCloudSearchDomain provide() {
        return client;
    }
}
