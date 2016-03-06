package com.coding4people.mosquitoreport.api.factories;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.container.ContainerRequestContext;

import org.junit.Test;
import org.mockito.Mock;

import com.coding4people.mosquitoreport.api.WithService;
import com.coding4people.mosquitoreport.api.models.User;
import com.coding4people.mosquitoreport.api.repositories.UserRepository;

public class CurrentUserFactoryTest extends WithService {
    @Mock
    UserRepository userRepository;

    @Mock
    ContainerRequestContext requestContext;

    @Test(expected = ForbiddenException.class)
    public void testThrowsForbiddenExceptionWhenThereIsNoAuthorizationHeader() {
        when(requestContext.getHeaderString("Authorization")).thenReturn(null);

        new CurrentUserFactory(userRepository, requestContext);
    }

    @Test(expected = ForbiddenException.class)
    public void testThrowsForbiddenExceptionWhenTokenIsInvalid() {
        when(requestContext.getHeaderString("Authorization")).thenReturn("Token i n v a l i d");

        new CurrentUserFactory(userRepository, requestContext);
    }

    @Test(expected = ForbiddenException.class)
    public void testThrowsForbiddenExceptionWhenUserNotFound() {
        when(requestContext.getHeaderString("Authorization"))
                .thenReturn("Token MDAwMDAwMDAtMDAwMC0wMDAwLTAwMDAtMDAwMDAwMDAwMDAw");
        when(userRepository.load("00000000-0000-0000-0000-000000000000")).thenReturn(null);

        new CurrentUserFactory(userRepository, requestContext);

        verify(userRepository).load("00000000-0000-0000-0000-000000000000");
    }

    @Test
    public void testProvide() {
        User user = new User();

        when(requestContext.getHeaderString("Authorization"))
                .thenReturn("Token MDAwMDAwMDAtMDAwMC0wMDAwLTAwMDAtMDAwMDAwMDAwMDAw");
        when(userRepository.load("00000000-0000-0000-0000-000000000000")).thenReturn(user);

        CurrentUserFactory factory = new CurrentUserFactory(userRepository, requestContext);

        verify(userRepository).load("00000000-0000-0000-0000-000000000000");
        
        assertEquals(user, factory.provide());
        
        factory.dispose(user);
    }
}
