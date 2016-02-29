package com.coding4people.mosquitoreport.api.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import com.coding4people.mosquitoreport.api.WithServer;
import com.coding4people.mosquitoreport.api.controllers.AuthFacebookController.AuthFacebookInput;
import com.coding4people.mosquitoreport.api.models.Email;
import com.coding4people.mosquitoreport.api.models.FacebookUser;
import com.coding4people.mosquitoreport.api.models.User;
import com.coding4people.mosquitoreport.api.repositories.EmailRepository;
import com.coding4people.mosquitoreport.api.repositories.FacebookUserRepository;
import com.coding4people.mosquitoreport.api.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.restfb.WebRequestor;

public class AuthFacebookControllerTest extends WithServer {
    @Mock
    WebRequestor webRequestor;

    @Mock
    EmailRepository emailRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    FacebookUserRepository facebookUserRepository;

    @Override
    protected ResourceConfig configure() {
        return super.configure().register(AuthFacebookController.class);
    }

    @Test
    public void testKnownToken() {
        FacebookUser facebookUser = new FacebookUser();
        facebookUser.setUserguid("00000000-0000-0000-0000-000000000000");

        AuthFacebookInput data = new AuthFacebookInput();
        data.setToken("validtoken");

        when(facebookUserRepository.load("validtoken")).thenReturn(facebookUser);

        Response response = target().path("/auth/facebook").request().post(Entity.json(data));

        assertEquals(200, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getHeaderString("Content-type"));

        verify(facebookUserRepository).load("validtoken");

        ObjectNode result = response.readEntity(ObjectNode.class);

        assertTrue(result.has("token"));
    }

    @Test
    public void testKnownEmail() throws IOException {
        Email email = new Email();
        email.setEmail("test@test.org");
        email.setUserguid("00000000-0000-0000-0000-000000000000");

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode facebookResponse = mapper.createObjectNode().put("id", "123456").put("first_name", "Rogério")
                .put("last_name", "Yokomizo").put("email", "test@test.org");

        AuthFacebookInput data = new AuthFacebookInput();
        data.setToken("validtoken");

        when(facebookUserRepository.load("validtoken")).thenReturn(null);
        when(webRequestor.executeGet(any())).thenReturn(new com.restfb.WebRequestor.Response(HttpURLConnection.HTTP_OK,
                mapper.writeValueAsString(facebookResponse)));
        when(emailRepository.load("test@test.org")).thenReturn(email);

        Response response = target().path("/auth/facebook").request().post(Entity.json(data));

        assertEquals(200, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getHeaderString("Content-type"));

        verify(facebookUserRepository).load("validtoken");
        
        ArgumentCaptor<FacebookUser> facebookUserCaptor = ArgumentCaptor.forClass(FacebookUser.class);
        
        verify(facebookUserRepository).save(facebookUserCaptor.capture());
        
        FacebookUser facebookUser = facebookUserCaptor.getValue();
        
        assertEquals("123456", facebookUser.getId());
        assertEquals("validtoken", facebookUser.getToken());
        assertEquals("00000000-0000-0000-0000-000000000000", facebookUser.getUserguid());

        ObjectNode result = response.readEntity(ObjectNode.class);

        assertTrue(result.has("token"));
    }
    
    @Test
    public void testNewUser() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode facebookResponse = mapper.createObjectNode().put("id", "123456").put("first_name", "Rogério")
                .put("last_name", "Yokomizo").put("email", "test@test.org");

        AuthFacebookInput data = new AuthFacebookInput();
        data.setToken("validtoken");

        when(facebookUserRepository.load("validtoken")).thenReturn(null);
        when(webRequestor.executeGet(any())).thenReturn(new com.restfb.WebRequestor.Response(HttpURLConnection.HTTP_OK,
                mapper.writeValueAsString(facebookResponse)));
        when(emailRepository.load("test@test.org")).thenReturn(null);

        Response response = target().path("/auth/facebook").request().post(Entity.json(data));

        assertEquals(200, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getHeaderString("Content-type"));

        verify(facebookUserRepository).load("validtoken");
        
        ArgumentCaptor<FacebookUser> facebookUserCaptor = ArgumentCaptor.forClass(FacebookUser.class);
        ArgumentCaptor<Email> emailCaptor = ArgumentCaptor.forClass(Email.class);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        
        verify(facebookUserRepository).save(facebookUserCaptor.capture());
        verify(emailRepository).save(emailCaptor.capture());
        verify(userRepository).save(userCaptor.capture());
        
        FacebookUser facebookUser = facebookUserCaptor.getValue();
        Email email = emailCaptor.getValue();
        User user = userCaptor.getValue();
        
        assertNotNull(facebookUser.getUserguid());
        assertEquals("123456", facebookUser.getId());
        assertEquals("validtoken", facebookUser.getToken());
        
        assertNotNull(email.getUserguid());
        assertEquals("test@test.org", email.getEmail());
        
        assertNotNull(user.getGuid());
        assertEquals("test@test.org", user.getEmail());
        assertEquals("Rogério", user.getFirstname());
        assertEquals("Yokomizo", user.getLastname());

        ObjectNode result = response.readEntity(ObjectNode.class);

        assertTrue(result.has("token"));
    }
}
