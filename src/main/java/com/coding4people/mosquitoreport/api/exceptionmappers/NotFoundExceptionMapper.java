package com.coding4people.mosquitoreport.api.exceptionmappers;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException exception) {
        String response = "{\"status\": \"error\", \"message\": \"Not found\"}";
        
        return Response.status(Status.NOT_FOUND).entity(response).type("application/json;charset=UTF-8").build();
    }
}
