package com.coding4people.mosquitoreport.api.buckets;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class BucketBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(PictureBucket.class).to(PictureBucket.class);
    }
}
