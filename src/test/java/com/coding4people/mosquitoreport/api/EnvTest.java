package com.coding4people.mosquitoreport.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class EnvTest {
    @Test
    public void testProperties() {
        assertFalse(Env.instance.get("NON-EXISTENT-ENV-VAR").isPresent());
        Env.instance.register("NON-EXISTENT-ENV-VAR", "Some string value");
        assertEquals("Some string value", Env.instance.get("NON-EXISTENT-ENV-VAR").get());
    }
}
