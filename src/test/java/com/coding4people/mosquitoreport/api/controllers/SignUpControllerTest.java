package com.coding4people.mosquitoreport.api.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.mockito.Mock;

import com.coding4people.mosquitoreport.api.WithServer;
import com.coding4people.mosquitoreport.api.controllers.SignUpController.SignUpInput;
import com.coding4people.mosquitoreport.api.models.Email;
import com.coding4people.mosquitoreport.api.repositories.EmailRepository;
import com.coding4people.mosquitoreport.api.repositories.UserRepository;

public class SignUpControllerTest extends WithServer {
    @Mock
    EmailRepository emailRepository;
    @Mock
    UserRepository userRepository;

    @Override
    protected ResourceConfig configure() {
        return super.configure().register(SignUpController.class);
    }

    @Test
    public void testSignUpEmailAlreadyExists() {
        SignUpInput input = new SignUpInput();
        input.setEmail("test@test.org");
        input.setPassword("123456");
        input.setFirstname("new firstname");
        input.setLastname("new lastname");

        when(emailRepository.load(input.getEmail())).thenReturn(new Email());

        Response response = target().path("/signup/email").request().post(Entity.json(input));

        assertEquals(400, response.getStatus());

        verify(emailRepository).load(eq(input.getEmail()));
    }
}
