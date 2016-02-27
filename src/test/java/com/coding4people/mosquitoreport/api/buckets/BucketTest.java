package com.coding4people.mosquitoreport.api.buckets;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;
import org.mockito.Mock;

import com.amazonaws.services.s3.AmazonS3Client;
import com.coding4people.mosquitoreport.api.WithService;

public class BucketTest extends WithService {
    @Mock
    AmazonS3Client amazonS3Client;
    
    @Test
    public void testPut() {
        ModelBucket bucket = getService(ModelBucket.class);
        
        InputStream stream = new ByteArrayInputStream("file content".getBytes());
        
        bucket.put("path", stream, 12L);
        
        verify(amazonS3Client).putObject(any());
    }
    
    public static class Model {}
    
    public static class ModelBucket extends Bucket {
        @Override
        protected String getBucketName() {
            return "model-bucket";
        }
    }
}
