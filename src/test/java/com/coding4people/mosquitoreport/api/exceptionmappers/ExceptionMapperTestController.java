package com.coding4people.mosquitoreport.api.exceptionmappers;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class ExceptionMapperTestController {

    @POST
    @Consumes("application/json")
    @Produces("application/json;charset=UTF-8")
    public String post(@Valid Data data) {
        return "\"Parameter is " + data.getParameter() + "\"";
    }

    public static class Data {
        @NotNull
        private String parameter;

        public String getParameter() {
            return parameter;
        }

        public void setParameter(String parameter) {
            this.parameter = parameter;
        }
    }

}
