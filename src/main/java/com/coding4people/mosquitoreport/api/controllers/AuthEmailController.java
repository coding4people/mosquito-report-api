package com.coding4people.mosquitoreport.api.controllers;

import java.util.Base64;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.coding4people.mosquitoreport.api.models.Email;
import com.coding4people.mosquitoreport.api.repositories.EmailRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Path("/auth/email")
public class AuthEmailController {
    @Inject
    EmailRepository emailRepository;

    @POST
    @Consumes("application/json")
    @Produces("application/json;charset=UTF-8")
    public ObjectNode auth(@Valid AuthInput input) {
        Email email = emailRepository.load(input.getEmail());
        
        if (email == null || !Email.checkPassword(input.getPassword(), email.getPassword())) {
            throw new ForbiddenException("Invalid email or password");
        }
        
        //TODO implement decent authentication
        final String token = Base64.getEncoder().encodeToString(email.getUserguid().getBytes());

        return new ObjectMapper().createObjectNode().put("token", token);
    }

    public static class AuthInput {
        @NotNull
        private String email;
        
        @NotNull
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
