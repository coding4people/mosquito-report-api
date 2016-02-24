package com.coding4people.mosquitoreport.api.exceptionmappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonMappingExceptionMapper implements ExceptionMapper<JsonMappingException> {

    @Override
    public Response toResponse(JsonMappingException exception) {
        if ("true".equals(System.getenv("MOSQUITO_REPORT_DEBUG"))) {
            exception.printStackTrace();
        } //TODO else log into newrelic
        
        ObjectNode error = new ObjectMapper().createObjectNode();
        error.put("status", "error");
        error.put("message", "Error mapping json");

        return Response.status(Status.BAD_REQUEST).entity(error).type("application/json;charset=UTF-8").build();
    }

}
