package com.coding4people.mosquitoreport.api.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Path("/healthcheck")
public class HealthCheckController {
    @GET
    @Consumes("application/json")
    @Produces("application/json;charset=UTF-8")
    public ObjectNode query() {
        return new ObjectMapper().createObjectNode().put("status", "ok");
    }
}
