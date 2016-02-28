package com.coding4people.mosquitoreport.api.model;

import org.junit.Test;

import com.coding4people.mosquitoreport.api.models.Email;

public class EmailTest {
    @Test(expected = IllegalArgumentException.class)
    public void testThrowsIllegalArgumentException() {
        Email.checkPassword("", "");
    }
}
