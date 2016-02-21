package com.coding4people.mosquitoreport.api.controllers;

import java.util.UUID;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.coding4people.mosquitoreport.api.models.Email;
import com.coding4people.mosquitoreport.api.models.User;
import com.coding4people.mosquitoreport.api.repositories.EmailRepository;
import com.coding4people.mosquitoreport.api.repositories.UserRepository;

@Path("/signup/email")
public class SignUpController {
    @Inject EmailRepository emailRepository;
    @Inject UserRepository userRepository;

    @POST
    @Consumes("application/json")
    @Produces("application/json;charset=UTF-8")
    public User signUp(@Valid SignUpInput input) {
        if (emailRepository.load(input.getEmail()) != null) {
            throw new BadRequestException("Email already registered");
        }
        
        final String guid = UUID.randomUUID().toString();
        
        User user = new User();
        user.setGuid(guid);
        user.setFirstname(input.getFirstname());
        user.setLastname(input.getLastname());
        user.setEmail(input.getEmail());
        
        Email email = new Email();
        email.setUserguid(guid);
        email.setEmail(input.getEmail());
        email.setPassword(Email.encryptPassword(input.getPassword()));
        
        //TODO made it asynchronous
        emailRepository.save(email);
        userRepository.save(user);
        
        return user;
    }

    public static class SignUpInput {
        @NotNull private String email;
        @NotNull private String firstname;
        @NotNull private String lastname;
        @NotNull private String password;
        
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
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
        
        public String getPassword() {
            return password;
        }
        
        public void setPassword(String password) {
            this.password = password;
        }
    }
}
