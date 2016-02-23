package com.coding4people.mosquitoreport.api.exceptionmappers;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException exception) {
        ObjectNode error = new ObjectMapper().createObjectNode();
        error.put("status", "error");
        error.put("message", "Resource not found");
        
        return Response.status(Status.NOT_FOUND).entity(error).type("application/json;charset=UTF-8").build();
    }
}
