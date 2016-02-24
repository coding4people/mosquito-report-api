package com.coding4people.mosquitoreport.api.exceptionmappers;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;

import com.coding4people.mosquitoreport.api.WithServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonMappingExceptionMapperTest extends WithServer {

    @Override
    protected ResourceConfig configure() {
        return super.configure().register(ExceptionMapperTestController.class)
                .register(JsonMappingExceptionMapper.class);
    }

    @Test
    public void testExtraParameter() {
        ObjectNode input = new ObjectMapper().createObjectNode();
        input.put("token", "...");
        input.put("extra_paramenter", "...");

        Response response = target().path("/").request().post(Entity.json(input));

        assertEquals(400, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getHeaderString("Content-type"));

        ObjectNode ObjectNode = response.readEntity(ObjectNode.class);

        assertEquals("error", ObjectNode.get("status").asText());
        assertEquals("Error mapping json", ObjectNode.get("message").asText());
    }

}
