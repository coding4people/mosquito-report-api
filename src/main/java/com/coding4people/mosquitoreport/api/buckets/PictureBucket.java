package com.coding4people.mosquitoreport.api.buckets;

import javax.inject.Inject;

import com.coding4people.mosquitoreport.api.Env;

public class PictureBucket extends Bucket {
    @Inject Env env;
    
    @Override
    protected String getBucketName() {
        return env.get("MOSQUITO_REPORT_BUCKET_NAME_PICTURE");
    }

}
