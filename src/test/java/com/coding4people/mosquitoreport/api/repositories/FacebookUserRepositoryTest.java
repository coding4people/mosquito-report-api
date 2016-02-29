package com.coding4people.mosquitoreport.api.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.coding4people.mosquitoreport.api.models.FacebookUser;

public class FacebookUserRepositoryTest {
    @Test
    public void testGetType() {
        assertEquals(FacebookUser.class, new FacebookUserRepository().getType());
    }
}
