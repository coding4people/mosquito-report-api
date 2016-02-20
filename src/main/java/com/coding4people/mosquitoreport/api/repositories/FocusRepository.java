package com.coding4people.mosquitoreport.api.repositories;

import javax.inject.Singleton;

import com.coding4people.mosquitoreport.api.models.Focus;

@Singleton
public class FocusRepository extends Repository<Focus> {
    @Override
    protected Class<Focus> getType() {
        return Focus.class;
    }
}
