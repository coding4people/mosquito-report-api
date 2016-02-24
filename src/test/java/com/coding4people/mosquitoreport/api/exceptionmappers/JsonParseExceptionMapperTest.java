package com.coding4people.mosquitoreport.api.exceptionmappers;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;

import com.coding4people.mosquitoreport.api.WithServer;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonParseExceptionMapperTest extends WithServer {

    @Override
    protected ResourceConfig configure() {
        return super.configure().register(ExceptionMapperTestController.class).register(JsonParseExceptionMapper.class);
    }

    @Test
    public void testInvalidJson() {
        Response response = target().path("/").request().post(Entity.json("invalid json"));

        assertEquals(400, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getHeaderString("Content-type"));
        
        ObjectNode ObjectNode = response.readEntity(ObjectNode.class);

        assertEquals("error", ObjectNode.get("status").asText());
        assertEquals("Invalid json", ObjectNode.get("message").asText());
    }

}
