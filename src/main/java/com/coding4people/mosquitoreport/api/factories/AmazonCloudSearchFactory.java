package com.coding4people.mosquitoreport.api.factories;

import org.glassfish.hk2.api.Factory;

import com.amazonaws.services.cloudsearchv2.AmazonCloudSearch;
import com.amazonaws.services.cloudsearchv2.AmazonCloudSearchClient;

public class AmazonCloudSearchFactory implements Factory<AmazonCloudSearch> {
    AmazonCloudSearch client;

    public AmazonCloudSearchFactory() {
        client = new AmazonCloudSearchClient();
    }

    @Override
    public void dispose(AmazonCloudSearch client) {
    }

    @Override
    public AmazonCloudSearch provide() {
        return client;
    }
}
