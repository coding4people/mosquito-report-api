package com.coding4people.mosquitoreport.api.exceptionmappers;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;

import com.coding4people.mosquitoreport.api.WithServer;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class BadRequestExceptionMapperTest extends WithServer {

    @Path("/bad-request")
    public static class TestErrorController {
        @GET
        @Produces("application/json;charset=UTF-8")
        public String producesInternalServerError() {
            throw new BadRequestException("fake message");
        }
    }

    @Override
    protected ResourceConfig configure() {
        return super.configure().register(TestErrorController.class).register(BadRequestExceptionMapper.class);
    }

    @Test
    public void testMissingParameter() {
        Response response = target().path("/bad-request").request().get();

        assertEquals(400, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getHeaderString("Content-type"));
        
        ObjectNode ObjectNode = response.readEntity(ObjectNode.class);
        
        assertEquals("error", ObjectNode.get("status").asText());
        assertEquals("fake message", ObjectNode.get("message").asText());
    }
}
