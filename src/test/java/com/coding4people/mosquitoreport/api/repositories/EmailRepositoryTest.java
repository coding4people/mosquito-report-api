package com.coding4people.mosquitoreport.api.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.coding4people.mosquitoreport.api.models.Email;

public class EmailRepositoryTest {
    @Test
    public void testGetType() {
        assertEquals(Email.class, new EmailRepository().getType());
    }
}
