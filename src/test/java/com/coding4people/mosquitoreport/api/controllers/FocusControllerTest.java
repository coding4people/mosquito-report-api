package com.coding4people.mosquitoreport.api.controllers;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;

import com.coding4people.mosquitoreport.api.WithServer;
import com.coding4people.mosquitoreport.api.controllers.FocusController.FocusPostInput;

public class FocusControllerTest extends WithServer {
    @Override
    protected ResourceConfig configure() {
        return super.configure().register(FocusController.class);
    }

    @Test
    public void testGetIt() {
        FocusPostInput data = new FocusPostInput();
        data.setLatitude("lat");
        data.setLongitude("lng");
        
        Response response = target().path("/focus").request()
                .post(Entity.json(data));

        assertEquals(200, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getHeaderString("Content-type"));
        assertEquals("\"lat\"", response.readEntity(String.class));
    }
}
