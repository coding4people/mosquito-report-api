package com.coding4people.mosquitoreport.api;

import java.util.Map;
import java.util.Optional;

import jersey.repackaged.com.google.common.collect.Maps;

public class Env {
    public final static Env instance = new Env();
    
    private Map<String, String> properties = Maps.newHashMap();
    
    public Optional<String> get(String key) {
        return Optional.ofNullable(properties.getOrDefault(key, System.getenv(key)));
    }
    
    public Env register(String key, String value) {
        properties.put(key, value);
        
        return this;
    }
}
