package com.coding4people.mosquitoreport.api.factories;

import javax.inject.Inject;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.container.ContainerRequestContext;

import org.glassfish.hk2.api.Factory;
import org.glassfish.jersey.process.internal.RequestScoped;

import com.coding4people.mosquitoreport.api.auth.AuthenticationService;
import com.coding4people.mosquitoreport.api.models.User;
import com.coding4people.mosquitoreport.api.repositories.UserRepository;

@RequestScoped
public class CurrentUserFactory implements Factory<User> {
    User user;

    @Inject
    public CurrentUserFactory(UserRepository userRepository, ContainerRequestContext requestContext, AuthenticationService authenticationService) {
        String authorization = requestContext.getHeaderString("Authorization");

        if (authorization == null || authorization.isEmpty() || !authorization.startsWith("Token ")) {
            throw new ForbiddenException("Missing 'Authorization' HTTP header");
        }

        user = userRepository.load(authenticationService.identify(authorization.substring(6)));

        if (user == null) {
            throw new ForbiddenException("User not authenticate");
        }
    }

    @Override
    public void dispose(User user) {
    }

    @Override
    public User provide() {
        return user;
    }
}
