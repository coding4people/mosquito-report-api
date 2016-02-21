package com.coding4people.mosquitoreport.api.repositories;

import javax.inject.Singleton;

import com.coding4people.mosquitoreport.api.models.User;

@Singleton
public class UserRepository extends Repository<User> {
    @Override
    protected Class<User> getType() {
        return User.class;
    }
}
