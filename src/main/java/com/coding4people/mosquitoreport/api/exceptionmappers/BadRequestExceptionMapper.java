package com.coding4people.mosquitoreport.api.exceptionmappers;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {
    @Override
    public Response toResponse(BadRequestException exception) {
        ObjectNode error = new ObjectMapper().createObjectNode();
        error.put("status", "error");
        error.put("message", exception.getMessage());
        
        return Response.status(Status.BAD_REQUEST).entity(error).type("application/json;charset=UTF-8").build();
    }
}
