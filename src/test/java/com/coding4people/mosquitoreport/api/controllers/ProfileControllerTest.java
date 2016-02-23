package com.coding4people.mosquitoreport.api.controllers;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.mockito.Mock;

import com.coding4people.mosquitoreport.api.WithServer;
import com.coding4people.mosquitoreport.api.models.User;
import com.coding4people.mosquitoreport.api.repositories.UserRepository;

public class ProfileControllerTest extends WithServer {
    @Mock
    UserRepository userRepository;

    User currentUser = new User();;

    @Override
    protected ResourceConfig configure() {
        return super.configure().register(ProfileController.class);
    }
    
    @Override
    protected User getCurrentUser() {
        return currentUser;
    }

    @Test
    public void testGet() {
        currentUser.setGuid("my guid");
        currentUser.setEmail("my email");
        currentUser.setFirstname("my firstname");
        currentUser.setLastname("my lastname");
        currentUser.setLocation("my location");
        currentUser.setFacebookurl("my facebookurl");
        currentUser.setTwitter("my twitter");
        currentUser.setProfilepictureguid("my profilepictureguid");

        Response response = target().path("/profile").request().get();

        assertEquals(200, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getHeaderString("Content-type"));

        User user = response.readEntity(User.class);

        assertEquals("my guid", user.getGuid());
        assertEquals("my email", user.getEmail());
        assertEquals("my firstname", user.getFirstname());
        assertEquals("my lastname", user.getLastname());
        assertEquals("my location", user.getLocation());
        assertEquals("my facebookurl", user.getFacebookurl());
        assertEquals("my twitter", user.getTwitter());
        assertEquals("my profilepictureguid", user.getProfilepictureguid());
    }
}
