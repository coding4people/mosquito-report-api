package com.coding4people.mosquitoreport.api;

public class Env {
    
    public String get(String key) {
        return System.getenv(key);
    }

}
