package com.coding4people.mosquitomap.api.controllers;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/focus")
public class FocusController {
    @POST
    @Consumes("application/json")
    @Produces("application/json;charset=UTF-8")
    public String post(@Valid FocusPostInput focus) {
        return "\"" + focus.getLatitude() + "\"";
    }
    
    public static class FocusPostInput {
        @NotNull
        private String longitude;
        
        @NotNull
        private String latitude;

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }
    }
}
