package com.coding4people.mosquitoreport.api.factories;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;

import com.amazonaws.services.s3.AmazonS3Client;

public class AmazonS3ClientFactory implements Factory<AmazonS3Client> {
    AmazonS3Client client;

    @Inject
    public AmazonS3ClientFactory() {
        client = new AmazonS3Client();
    }

    @Override
    public void dispose(AmazonS3Client client) {
    }

    @Override
    public AmazonS3Client provide() {
        return client;
    }
}
