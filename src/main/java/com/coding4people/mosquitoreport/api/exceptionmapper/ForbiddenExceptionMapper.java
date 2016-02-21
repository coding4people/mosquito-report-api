package com.coding4people.mosquitoreport.api.exceptionmapper;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {
    @Override
    public Response toResponse(ForbiddenException exception) {
        ObjectNode error = new ObjectMapper().createObjectNode();
        error.put("status", "error");
        error.put("message", exception.getMessage());
        
        return Response.status(Status.FORBIDDEN).entity(error).type("application/json;charset=UTF-8").build();
    }
}
