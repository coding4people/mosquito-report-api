package com.coding4people.mosquitoreport.api.repositories;

import javax.inject.Singleton;

import com.coding4people.mosquitoreport.api.models.FacebookUser;

@Singleton
public class FacebookUserRepository extends Repository<FacebookUser> {
    @Override
    protected Class<FacebookUser> getType() {
        return FacebookUser.class;
    }
}
