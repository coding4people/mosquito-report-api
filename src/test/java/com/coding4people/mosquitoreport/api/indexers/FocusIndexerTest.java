package com.coding4people.mosquitoreport.api.indexers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.coding4people.mosquitoreport.api.models.Focus;

public class FocusIndexerTest {
    @Test
    public void testGetType() {
        assertEquals(Focus.class, new FocusIndexer().getType());
    }
}
