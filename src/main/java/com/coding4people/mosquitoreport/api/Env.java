package com.coding4people.mosquitoreport.api;

import java.util.Map;

import jersey.repackaged.com.google.common.collect.Maps;

public class Env {
    public final static Env instance = new Env();
    
    private Map<String, String> properties = Maps.newHashMap();
    
    public String get(String key) {
        return properties.getOrDefault(key, System.getenv(key));
    }
    
    public Env register(String key, String value) {
        properties.put(key, value);
        
        return this;
    }
}
