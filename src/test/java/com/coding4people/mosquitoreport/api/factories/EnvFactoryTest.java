package com.coding4people.mosquitoreport.api.factories;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class EnvFactoryTest {

    @Test
    public void testProvide() {
        assertNotNull(new EnvFactory().provide());
    }
}
