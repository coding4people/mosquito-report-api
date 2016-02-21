package com.coding4people.mosquitoreport.api.controllers;

import java.util.Date;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.coding4people.mosquitoreport.api.indexers.FocusIndexer;
import com.coding4people.mosquitoreport.api.models.Focus;
import com.coding4people.mosquitoreport.api.repositories.FocusRepository;

@Path("/focus")
public class FocusController {
    @Inject
    FocusRepository focusRepository;
    @Inject
    FocusIndexer focusIndexer;

    @POST
    @Consumes("application/json")
    @Produces("application/json;charset=UTF-8")
    public Focus post(@Valid FocusPostInput input) {
        Focus focus = new Focus();
        focus.setGuid(java.util.UUID.randomUUID().toString());
        focus.setLatlon(input.getLatlon());
        focus.setCreateat(Long.toString(new Date().getTime()));

        focusRepository.save(focus);
        focusIndexer.index(focus);

        return focus;
    }

    @POST
    @Path("/query")
    @Consumes("application/json")
    @Produces("application/json;charset=UTF-8")
    public Object query(@Valid FocusQueryInput input) {
        return focusIndexer.search(input.getLatlonnw(), input.getLatlonse());
    }

    public static class FocusPostInput {
        @NotNull
        private String latlon;

        public String getLatlon() {
            return latlon;
        }

        public void setLatlon(String latlon) {
            this.latlon = latlon;
        }
    }
    
    public static class FocusQueryInput {
        @NotNull
        private String latlonnw;
        
        @NotNull
        private String latlonse;

        public String getLatlonnw() {
            return latlonnw;
        }

        public void setLatlonnw(String latlonnw) {
            this.latlonnw = latlonnw;
        }

        public String getLatlonse() {
            return latlonse;
        }

        public void setLatlonse(String latlonse) {
            this.latlonse = latlonse;
        }
    }
}
