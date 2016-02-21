package com.coding4people.mosquitoreport.api.repositories;

import javax.inject.Singleton;

import com.coding4people.mosquitoreport.api.models.Email;

@Singleton
public class EmailRepository extends Repository<Email> {
    @Override
    protected Class<Email> getType() {
        return Email.class;
    }
}
