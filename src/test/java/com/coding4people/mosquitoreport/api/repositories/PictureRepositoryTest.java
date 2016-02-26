package com.coding4people.mosquitoreport.api.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.coding4people.mosquitoreport.api.models.Picture;

public class PictureRepositoryTest {
    @Test
    public void testGetType() {
        assertEquals(Picture.class, new PictureRepository().getType());
    }
}
