package com.coding4people.mosquitoreport.api.controllers;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.hibernate.validator.constraints.NotEmpty;

import com.coding4people.mosquitoreport.api.models.User;
import com.coding4people.mosquitoreport.api.repositories.UserRepository;

@Path("/profile")
public class ProfileController {
    @Inject UserRepository userRepository;
    @Inject User currentUser;

    /**
     * @api {get} /profile Show user profile details
     * @apiGroup User
     * 
     * @apiSuccessExample Success-Headers:
     *     HTTP/1.1 200 OK
     *
     * @apiSuccessExample Success-Response:
     *     {
     *       "guid": "f68079be-2c23-41dd-9ca2-0cc6aa368c5a",
     *       "email": "test@test.org",
     *       "firstname": "Rogério",
     *       "lastname": "Yokomizo",
     *       "location": null,
     *       "facebookurl": null,
     *       "twitter": null,
     *       "profilepictureguid": null
     *     }
     */
    @GET
    @Produces("application/json;charset=UTF-8")
    public User get() {
        return currentUser;
    }
    
    @POST
    @Consumes("application/json")
    @Produces("application/json;charset=UTF-8")
    public User post(@Valid ProfilePostInput input) {
        currentUser.setFirstname(input.getFirstname());
        currentUser.setLastname(input.getLastname());
        currentUser.setLocation(input.getLocation());
        currentUser.setFacebookurl(input.getFacebookurl());
        currentUser.setTwitter(input.getTwitter());
        
        userRepository.save(currentUser);
        
        return currentUser;
    }

    public static class ProfilePostInput {
        @NotNull
        @NotEmpty
        private String firstname;
        
        private String lastname;
        
        private String location;
        
        private String facebookurl;
        
        private String twitter;

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

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getFacebookurl() {
            return facebookurl;
        }

        public void setFacebookurl(String facebookurl) {
            this.facebookurl = facebookurl;
        }

        public String getTwitter() {
            return twitter;
        }

        public void setTwitter(String twitter) {
            this.twitter = twitter;
        }
    }
}
