package com.coding4people.mosquitoreport.api.controllers;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;

import com.coding4people.mosquitoreport.api.WithServer;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class HealthCheckControllerTest extends WithServer {
    @Override
    protected ResourceConfig configure() {
        return super.configure().register(HealthCheckController.class);
    }

    @Test
    public void testAuthFailed() {
        Response response = target().path("/healthcheck").request().get();

        assertEquals(200, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getHeaderString("Content-type"));
        
        ObjectNode status = response.readEntity(ObjectNode.class);
        
        assertEquals("ok", status.get("status").asText());
    }
}
