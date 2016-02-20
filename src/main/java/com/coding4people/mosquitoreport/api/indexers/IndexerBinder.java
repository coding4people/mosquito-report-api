package com.coding4people.mosquitoreport.api.indexers;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class IndexerBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(FocusIndexer.class).to(FocusIndexer.class);
    }
}
