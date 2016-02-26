package com.coding4people.mosquitoreport.api.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.coding4people.mosquitoreport.api.models.Focus;

public class FocusRepositoryTest {
    @Test
    public void testGetType() {
        assertEquals(Focus.class, new FocusRepository().getType());
    }
}
