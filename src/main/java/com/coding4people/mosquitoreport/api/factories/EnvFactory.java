package com.coding4people.mosquitoreport.api.factories;

import org.glassfish.hk2.api.Factory;

import com.coding4people.mosquitoreport.api.Env;

public class EnvFactory implements Factory<Env> {
    Env env;
    
    public EnvFactory() {
        this(new Env());
    }
    
    public EnvFactory(Env env) {
        this.env = env;
    }
    
    @Override
    public void dispose(Env env) {
    }

    @Override
    public Env provide() {
        return env;
    }
}
