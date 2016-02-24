package com.coding4people.mosquitoreport.api.exceptionmappers;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;

import com.coding4people.mosquitoreport.api.WithServer;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class NotFoundExceptionMapperTest extends WithServer {

    @Override
    protected ResourceConfig configure() {
        return super.configure().register(NotFoundExceptionMapper.class);
    }

    @Test
    public void testMissingParameter() {
        Response response = target().path("/non-existing-resource").request().get();

        assertEquals(404, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getHeaderString("Content-type"));
        
        ObjectNode ObjectNode = response.readEntity(ObjectNode.class);

        assertEquals("error", ObjectNode.get("status").asText());
        assertEquals("Not found", ObjectNode.get("message").asText());
    }

}
