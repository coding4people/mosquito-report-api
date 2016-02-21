package com.coding4people.mosquitoreport.api.buckets;

import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

abstract public class Bucket {
    @Inject AmazonS3Client amazonS3Client;
    
    abstract protected String getBucketName();
    
    @PostConstruct
    protected void postConstruct() {
        createBucket();
        postCreateBucket();
    }
    
    public void put(String path, InputStream fileInputStream, Long contentLenght) {
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(contentLenght);
        
        amazonS3Client.putObject(new PutObjectRequest(getBucketName(), path, fileInputStream, meta));
    }
    
    protected void postCreateBucket() {
    }
    
    protected void createBucket() {
        //TODO create bucket on construct
    }

}
