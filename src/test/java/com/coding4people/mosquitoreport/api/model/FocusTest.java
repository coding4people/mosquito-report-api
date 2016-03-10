package com.coding4people.mosquitoreport.api.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.coding4people.mosquitoreport.api.models.Focus;

public class FocusTest {
    @Test
    public void testGetSearchId() {
        Focus focus = new Focus();
        focus.setGuid("00000000-0000-0000-0000-000000000000");
        focus.setCreateat("1457649270");
        
        assertEquals("00000000-0000-0000-0000-000000000000_1457649270", focus.getSearchId());
    }
}
