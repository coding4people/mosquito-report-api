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
import com.coding4people.mosquitoreport.api.models.User;
import com.coding4people.mosquitoreport.api.repositories.FocusRepository;

@Path("/focus")
public class PostFocusController {
    @Inject
    FocusRepository focusRepository;
    @Inject
    FocusIndexer focusIndexer;
    @Inject
    User currentUser;

    @POST
    @Consumes("application/json")
    @Produces("application/json;charset=UTF-8")
    public Focus post(@Valid FocusPostInput input) {
        Focus focus = new Focus();
        focus.setGuid(java.util.UUID.randomUUID().toString());
        focus.setLatlon(input.getLatlon());
        focus.setNotes(input.getNotes());
        focus.setAuthoruserguid(currentUser.getGuid());
        focus.setThumbsup("0");
        focus.setCreateat(Long.toString(new Date().getTime()));

        focusRepository.save(focus);
        focusIndexer.index(focus);

        return focus;
    }

    public static class FocusPostInput {
        @NotNull
        private String latlon;
        
        private String notes;

        public String getLatlon() {
            return latlon;
        }

        public void setLatlon(String latlon) {
            this.latlon = latlon;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }
    }
}
