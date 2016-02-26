package com.coding4people.mosquitoreport.api;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

abstract public class GenericBinder<T> extends AbstractBinder {
    abstract public Class<T> getType();
    
    @Override
    protected void configure() {
        bind(getType()).to(getType());
    }
}
