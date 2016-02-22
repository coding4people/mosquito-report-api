package com.coding4people.mosquitoreport.api.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.mockito.Mock;

import com.coding4people.mosquitoreport.api.WithServer;
import com.coding4people.mosquitoreport.api.controllers.AuthEmailController.AuthInput;
import com.coding4people.mosquitoreport.api.models.Email;
import com.coding4people.mosquitoreport.api.repositories.EmailRepository;

public class AuthControllerTest extends WithServer {
    @Mock
    private EmailRepository emailRepository;

    @Override
    protected ResourceConfig configure() {
        return super.configure().register(AuthEmailController.class);
    }
    
    @Test
    public void testAuthFailed() {
    	AuthInput data = new AuthInput();
        data.setEmail("test@test.org");
        data.setPassword("fakepassword");
        
        Email email = new Email();
        email.setEmail("test@test.org");
        email.setPassword("$2a$12$JZP7DXIzHFnqBI0aj/d0p.ub4SPRe7Dph/OEcTwCJxdODbXLoERbe");

        when(emailRepository.load("test@test.org")).thenReturn(email);

        Response response = target().path("/auth/email").request().post(Entity.json(data));

        assertEquals(403, response.getStatus());

        verify(emailRepository).load("test@test.org");
    }

    //@Test
    public void testAuthSuccess() {
    	AuthInput data = new AuthInput();
        data.setEmail("me@ro.ger.io");
        data.setPassword("asdfgh");
        
        Email email = new Email();
        email.setEmail("me@ro.ger.io");
        email.setPassword("$2a$12$JZP7DXIzHFnqBI0aj/d0p.ub4SPRe7Dph/OEcTwCJxdODbXLoERbe");

        when(emailRepository.load("test@test.org")).thenReturn(email);

        Response response = target().path("/auth/email").request().post(Entity.json(data));

        assertEquals(200, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getHeaderString("Content-type"));

        Email result = response.readEntity(Email.class);
        verify(emailRepository).load("test@test.org");
        assertEquals("test@test.org", result.getEmail());
    }
}
