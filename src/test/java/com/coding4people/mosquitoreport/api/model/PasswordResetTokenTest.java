package com.coding4people.mosquitoreport.api.model;

import org.junit.Test;

import com.coding4people.mosquitoreport.api.models.PasswordResetToken;

public class PasswordResetTokenTest {
    @Test(expected = IllegalArgumentException.class)
    public void testThrowsIllegalArgumentException() {
        PasswordResetToken.checkToken("", "");
    }
}
