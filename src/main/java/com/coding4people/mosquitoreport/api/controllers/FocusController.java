package com.coding4people.mosquitoreport.api.controllers;

import java.util.Date;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.coding4people.mosquitoreport.api.models.Focus;
import com.coding4people.mosquitoreport.api.repositories.FocusRepository;

@Path("/focus")
public class FocusController {
    @Inject FocusRepository focusRepository;
    
    @POST
    @Consumes("application/json")
    @Produces("application/json;charset=UTF-8")
    public Focus post(@Valid FocusPostInput input) {
        Focus focus = new Focus();
        focus.setGuid(java.util.UUID.randomUUID().toString());
        focus.setLatitude(input.getLatitude());
        focus.setLongitude(input.getLongitude());
        focus.setCreateAt(Long.toString(new Date().getTime()));
        
        focusRepository.save(focus);
        
        return focus;
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
