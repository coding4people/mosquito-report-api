package com.coding4people.mosquitoreport.api.repositories;

import javax.inject.Singleton;

import com.coding4people.mosquitoreport.api.models.PasswordResetToken;

@Singleton
public class PasswordResetTokenRepository extends Repository<PasswordResetToken> {
    @Override
    protected Class<PasswordResetToken> getType() {
        return PasswordResetToken.class;
    }
}
