package com.coding4people.mosquitoreport.api.factories;

import org.glassfish.hk2.api.Factory;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;

public class AWSCredentialsProviderFactory implements Factory<AWSCredentialsProvider> {
    @Override
    public void dispose(AWSCredentialsProvider provider) {
    }

    @Override
    public AWSCredentialsProvider provide() {
        return new DefaultAWSCredentialsProviderChain();
    }
}
