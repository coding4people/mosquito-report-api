package com.coding4people.mosquitoreport.api.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.coding4people.mosquitoreport.api.models.ThumbsUp;

public class ThumbsUpRepositoryTest {
    @Test
    public void testGetType() {
        assertEquals(ThumbsUp.class, new ThumbsUpRepository().getType());
    }
}
