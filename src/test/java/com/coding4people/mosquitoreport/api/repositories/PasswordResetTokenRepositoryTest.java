package com.coding4people.mosquitoreport.api.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.coding4people.mosquitoreport.api.models.PasswordResetToken;

public class PasswordResetTokenRepositoryTest {
    @Test
    public void testGetType() {
        assertEquals(PasswordResetToken.class, new PasswordResetTokenRepository().getType());
    }
}
