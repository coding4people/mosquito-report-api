package com.coding4people.mosquitoreport.api.buckets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.InternalServerErrorException;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Singleton
abstract public class Bucket {
    @Inject
    AmazonS3Client amazonS3Client;

    abstract protected String getBucketName();

    @PostConstruct
    protected void postConstruct() {
        createBucket();
        postCreateBucket();
    }

    public Object put(String path, InputStream fileInputStream, Long contentLenght) {
        if (contentLenght == null || contentLenght <= 0) {
            FindContentLengthResult result = findContentLength(fileInputStream);
            contentLenght = result.getContentLength();
            fileInputStream = result.getFileInputStream();
        }

        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(contentLenght);

        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);

        return amazonS3Client.putObject(new PutObjectRequest(getBucketName(), path, fileInputStream, meta).withAccessControlList(acl));
    }

    protected void postCreateBucket() {
    }

    protected void createBucket() {
        // TODO create bucket on construct
    }
    
    protected ByteArrayOutputStream bufferInputStream(InputStream fileInputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = fileInputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        
        return buffer;
    }
    
    protected FindContentLengthResult findContentLength(InputStream fileInputStream) {
        try {
            ByteArrayOutputStream buffer = bufferInputStream(fileInputStream);
            FindContentLengthResult result = new FindContentLengthResult();
            result.setContentLength(Long.valueOf(buffer.size()));
            result.setFileInputStream(new ByteArrayInputStream(buffer.toByteArray()));
            
            return result;
        } catch (IOException e) {
            // TODO handle and log
            e.printStackTrace();

            throw new InternalServerErrorException("Error uploading image");
        }
        
    }
    
    public static class FindContentLengthResult {
        private InputStream fileInputStream;
        private Long contentLength;
        
        public InputStream getFileInputStream() {
            return fileInputStream;
        }
        
        public void setFileInputStream(InputStream fileInputStream) {
            this.fileInputStream = fileInputStream;
        }
        
        public Long getContentLength() {
            return contentLength;
        }
        
        public void setContentLength(Long contentLenght) {
            this.contentLength = contentLenght;
        }
    }
}
