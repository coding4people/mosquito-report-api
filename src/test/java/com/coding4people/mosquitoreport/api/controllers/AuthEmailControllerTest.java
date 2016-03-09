package com.coding4people.mosquitoreport.api.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.coding4people.mosquitoreport.api.WithServer;
import com.coding4people.mosquitoreport.api.auth.AuthenticationService;
import com.coding4people.mosquitoreport.api.controllers.AuthEmailController.AuthInput;
import com.coding4people.mosquitoreport.api.models.Email;
import com.coding4people.mosquitoreport.api.repositories.EmailRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class AuthEmailControllerTest extends WithServer {
    @Mock
    private EmailRepository emailRepository;
    
    @Mock
    AuthenticationService authenticationService;

    private Email email;

    @Override
    protected ResourceConfig configure() {
        return super.configure().register(AuthEmailController.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        email = new Email();
        email.setUserguid("00000000-0000-0000-0000-000000000000");
        email.setEmail("test@test.org");
        email.setPassword("$2a$12$6XwAD7Zr62LpR7lcJ4Ulk.D.PieyA2eRPLpFSA0bQ5hsVZU0tcDta");
    }

    @Test
    public void testAuthFailed() {
        AuthInput data = new AuthInput();
        data.setEmail("test@test.org");
        data.setPassword("fakepassword");

        when(emailRepository.load("test@test.org")).thenReturn(email);

        Response response = target().path("/auth/email").request().post(Entity.json(data));

        assertEquals(403, response.getStatus());

        verify(emailRepository).load("test@test.org");
    }

    @Test
    public void testAuthSuccess() {
        AuthInput data = new AuthInput();
        data.setEmail("test@test.org");
        data.setPassword("123456");

        when(emailRepository.load("test@test.org")).thenReturn(email);
        when(authenticationService.generateToken("00000000-0000-0000-0000-000000000000")).thenReturn("token");

        Response response = target().path("/auth/email").request().post(Entity.json(data));

        assertEquals(200, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getHeaderString("Content-type"));

        ObjectNode result = response.readEntity(ObjectNode.class);
        verify(emailRepository).load("test@test.org");
        assertEquals("token", result.get("token").asText());
    }
}
