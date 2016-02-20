package com.coding4people.mosquitoreport.api;

import org.glassfish.hk2.api.Factory;

public class InstanceFactory<T> implements Factory<T> {
    private T instance = null;

    public InstanceFactory() {
    }
    
    public InstanceFactory(T instance) {
        this.instance = instance;
    }
    
    public void setInstance(T instance) {
        this.instance = instance;
    }

    @Override
    public void dispose(T t) {
    }

    @Override
    public T provide() {
        return instance;
    }
}
