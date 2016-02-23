package com.coding4people.mosquitoreport.api.exceptionmappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class InternalServerErrorExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable exception) {
        // TODO log it like a real developer (:
        exception.printStackTrace();
        
        ObjectNode error = new ObjectMapper().createObjectNode();
        error.put("status", "error");
        error.put("message", "Internal server error");
        
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(error).type("application/json;charset=UTF-8")
                .build();
    }
}
