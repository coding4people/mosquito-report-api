package com.coding4people.mosquitoreport.api.controllers;

import java.util.Base64;
import java.util.UUID;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.coding4people.mosquitoreport.api.models.Email;
import com.coding4people.mosquitoreport.api.models.FacebookUser;
import com.coding4people.mosquitoreport.api.models.User;
import com.coding4people.mosquitoreport.api.repositories.EmailRepository;
import com.coding4people.mosquitoreport.api.repositories.FacebookUserRepository;
import com.coding4people.mosquitoreport.api.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultJsonMapper;
import com.restfb.Facebook;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.WebRequestor;

@Path("/auth/facebook")
public class AuthFacebookController {
    @Inject
    WebRequestor webRequestor;

    @Inject
    EmailRepository emailRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    FacebookUserRepository facebookUserRepository;

    @POST
    @Consumes("application/json")
    @Produces("application/json;charset=UTF-8")
    public ObjectNode auth(@Valid AuthFacebookInput input) {
        final FacebookUser facebookUser = facebookUserRepository.load(input.getToken());
        
        final String guid;
        
        if (facebookUser == null) {
            FacebookClient facebookClient = new DefaultFacebookClient(input.getToken(), webRequestor,
                    new DefaultJsonMapper(), Version.VERSION_2_5);
            
            FacebookResponse facebookResponse = facebookClient.fetchObject("me", FacebookResponse.class,
                    Parameter.with("fields", "id,first_name,last_name,email,location,link"));
            
            final Email email = emailRepository.load(facebookResponse.getEmail());
            
            if (email == null) {
                guid = UUID.randomUUID().toString();
                
                final User newUser = new User();
                newUser.setGuid(guid);
                newUser.setFirstname(facebookResponse.getFirstname());
                newUser.setLastname(facebookResponse.getLastname());
                newUser.setEmail(facebookResponse.getEmail());
                
                final Email newEmail = new Email();
                newEmail.setUserguid(guid);
                newEmail.setEmail(facebookResponse.getEmail());
                
                //TODO made it asynchronous
                emailRepository.save(newEmail);
                userRepository.save(newUser);
            } else {
                guid = email.getUserguid();
            }
            
            final FacebookUser newFacebookUser = new FacebookUser();
            
            newFacebookUser.setUserguid(guid);
            newFacebookUser.setToken(input.getToken());
            newFacebookUser.setId(facebookResponse.getId());
            
            facebookUserRepository.save(newFacebookUser);
        } else {
            guid = facebookUser.getUserguid();
        }
        
        //TODO implement decent authentication
        final String token = Base64.getEncoder().encodeToString(guid.getBytes());

        return new ObjectMapper().createObjectNode().put("token", token);
    }

    public static class AuthFacebookInput {
        @NotNull
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    public static class FacebookResponse {
        @Facebook String id;
        @Facebook("first_name")
        String firstname;
        @Facebook("last_name")
        String lastname;
        @Facebook String email;
        @Facebook String location;
        @Facebook  String link;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
