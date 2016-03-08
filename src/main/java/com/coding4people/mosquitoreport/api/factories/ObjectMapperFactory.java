package com.coding4people.mosquitoreport.api.factories;

import org.glassfish.hk2.api.Factory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperFactory implements Factory<ObjectMapper> {
    @Override
    public ObjectMapper provide() {
        return new ObjectMapper();
    }

    @Override
    public void dispose(ObjectMapper instance) {
    }
}
