package com.coding4people.mosquitoreport.api.factories;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.glassfish.hk2.api.Factory;

public class ExecutorServiceFactory implements Factory<ExecutorService> {
    @Override
    public ExecutorService provide() {
        return Executors.newCachedThreadPool();
    }

    @Override
    public void dispose(ExecutorService instance) {
    }
}
