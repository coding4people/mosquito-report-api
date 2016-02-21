package com.coding4people.mosquitoreport.api.controllers;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.coding4people.mosquitoreport.api.indexers.FocusIndexer;

@Path("/focus")
public class FocusController {
    @Inject
    FocusIndexer focusIndexer;

    @POST
    @Path("/query")
    @Consumes("application/json")
    @Produces("application/json;charset=UTF-8")
    public Object query(@Valid FocusQueryInput input) {
        return focusIndexer.search(input.getLatlonnw(), input.getLatlonse());
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
