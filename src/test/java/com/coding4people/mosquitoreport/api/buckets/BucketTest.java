package com.coding4people.mosquitoreport.api.buckets;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.InternalServerErrorException;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Grant;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.coding4people.mosquitoreport.api.WithService;

public class BucketTest extends WithService {
    @Mock
    AmazonS3Client amazonS3Client;

    @Test(expected = InternalServerErrorException.class)
    public void testThrowsInternalServerErrorException() throws IOException {
        ModelBucket bucket = getService(ModelBucket.class);
        
        InputStream stream = new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException();
            }
        };
        
        bucket.put("path", stream, null);
    }
    
    @Test
    public void testPut() {
        ModelBucket bucket = getService(ModelBucket.class);

        InputStream stream = new ByteArrayInputStream("file content".getBytes());

        ArgumentCaptor<PutObjectRequest> requestCaptor = ArgumentCaptor.forClass(PutObjectRequest.class);

        PutObjectResult expected = new PutObjectResult();

        when(amazonS3Client.putObject(requestCaptor.capture())).thenReturn(expected);

        assertEquals(expected, bucket.put("path", stream, 12L));
        PutObjectRequest request = requestCaptor.getValue();

        assertEquals("model-bucket", request.getBucketName());
        assertEquals("path", request.getKey());
        assertEquals(stream, request.getInputStream());
        assertEquals(12L, request.getMetadata().getContentLength());
        
        List<Grant> grants = request.getAccessControlList().getGrantsAsList();
        
        assertEquals(1, grants.size());
        assertEquals(GroupGrantee.AllUsers, grants.get(0).getGrantee());
        assertEquals(Permission.Read, grants.get(0).getPermission());
    }
    
    @Test
    public void testPutWithoutContentLenght() {
        ModelBucket bucket = getService(ModelBucket.class);

        InputStream stream = new ByteArrayInputStream("file content".getBytes());

        ArgumentCaptor<PutObjectRequest> requestCaptor = ArgumentCaptor.forClass(PutObjectRequest.class);

        PutObjectResult expected = new PutObjectResult();

        when(amazonS3Client.putObject(requestCaptor.capture())).thenReturn(expected);

        assertEquals(expected, bucket.put("path", stream, null));
        PutObjectRequest request = requestCaptor.getValue();
        Scanner scanner = new Scanner(request.getInputStream());

        assertEquals("model-bucket", request.getBucketName());
        assertEquals("path", request.getKey());
        assertEquals("file content", scanner.useDelimiter("\\A").next());
        assertEquals(12L, request.getMetadata().getContentLength());
        
        List<Grant> grants = request.getAccessControlList().getGrantsAsList();
        
        assertEquals(1, grants.size());
        assertEquals(GroupGrantee.AllUsers, grants.get(0).getGrantee());
        assertEquals(Permission.Read, grants.get(0).getPermission());
        
        scanner.close();
    }

    public static class Model {
    }

    public static class ModelBucket extends Bucket {
        @Override
        protected String getBucketName() {
            return "model-bucket";
        }
    }
}
