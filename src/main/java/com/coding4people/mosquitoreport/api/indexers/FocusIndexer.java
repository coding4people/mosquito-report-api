package com.coding4people.mosquitoreport.api.indexers;

import javax.inject.Singleton;

import com.coding4people.mosquitoreport.api.models.Focus;

@Singleton
public class FocusIndexer extends Indexer<Focus> {
    @Override
    protected Class<Focus> getType() {
        return Focus.class;
    }
}
