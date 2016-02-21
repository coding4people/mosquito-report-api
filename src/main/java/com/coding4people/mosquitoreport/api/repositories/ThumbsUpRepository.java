package com.coding4people.mosquitoreport.api.repositories;

import javax.inject.Singleton;

import com.coding4people.mosquitoreport.api.models.ThumbsUp;

@Singleton
public class ThumbsUpRepository extends Repository<ThumbsUp> {
    @Override
    protected Class<ThumbsUp> getType() {
        return ThumbsUp.class;
    }
}
