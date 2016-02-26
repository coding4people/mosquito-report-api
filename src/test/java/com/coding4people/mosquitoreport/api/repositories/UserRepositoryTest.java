package com.coding4people.mosquitoreport.api.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.coding4people.mosquitoreport.api.models.User;

public class UserRepositoryTest {
    @Test
    public void testGetType() {
        assertEquals(User.class, new UserRepository().getType());
    }
}
