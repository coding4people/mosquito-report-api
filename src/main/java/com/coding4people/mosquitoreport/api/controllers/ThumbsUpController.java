package com.coding4people.mosquitoreport.api.controllers;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.coding4people.mosquitoreport.api.indexers.FocusIndexer;
import com.coding4people.mosquitoreport.api.models.Focus;
import com.coding4people.mosquitoreport.api.models.ThumbsUp;
import com.coding4people.mosquitoreport.api.models.User;
import com.coding4people.mosquitoreport.api.repositories.FocusRepository;
import com.coding4people.mosquitoreport.api.repositories.ThumbsUpRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Path("/thumbsup")
public class ThumbsUpController {
    @Inject
    ThumbsUpRepository thumbsUpRepository;
    @Inject
    FocusRepository focusRepository;
    @Inject
    FocusIndexer focusIndexer;
    @Inject
    User currentUser;

    @POST
    @Consumes("application/json")
    @Produces("application/json;charset=UTF-8")
    public ObjectNode thumbsUp(@Valid ThumbsUpPostInput input) {
        if (thumbsUpRepository.load(input.getFocusguid(), currentUser.getGuid()) != null) {
            throw new BadRequestException("User has already voted on this");
        }

        Focus focus = focusRepository.loadOrNotFound(input.getFocusguid()).thumbsup();

        ThumbsUp thumbsUp = new ThumbsUp();
        thumbsUp.setFocusguid(input.getFocusguid());
        thumbsUp.setUserguid(currentUser.getGuid());

        thumbsUpRepository.save(thumbsUp);
        focusRepository.save(focus);
        focusIndexer.index(focus); //TODO make it asynchronous

        return new ObjectMapper().createObjectNode().put("thumbsup", focus.getThumbsup());
    }

    public static class ThumbsUpPostInput {
        @NotNull
        private String focusguid;

        public String getFocusguid() {
            return focusguid;
        }

        public void setFocusguid(String focusguid) {
            this.focusguid = focusguid;
        }
    }
}
