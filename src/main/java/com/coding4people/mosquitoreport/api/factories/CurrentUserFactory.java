package com.coding4people.mosquitoreport.api.factories;

import java.util.Base64;

import javax.inject.Inject;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.container.ContainerRequestContext;

import org.glassfish.hk2.api.Factory;
import org.glassfish.jersey.process.internal.RequestScoped;

import com.coding4people.mosquitoreport.api.models.User;
import com.coding4people.mosquitoreport.api.repositories.UserRepository;

@RequestScoped
public class CurrentUserFactory implements Factory<User> {
    User user;

    @Inject
    public CurrentUserFactory(UserRepository userRepository, ContainerRequestContext requestContext) {
        String authorization = requestContext.getHeaderString("Authorization");

        if (authorization == null || authorization.isEmpty() || !authorization.startsWith("Token ")) {
            throw new ForbiddenException("Missing 'Authorization' HTTP header");
        }

        // TODO implement decent authorization algorithm
        String guid;
        try {
            guid = new String(Base64.getDecoder().decode(authorization.substring(6)));
        } catch (Throwable t) {
            throw new ForbiddenException("Invalid authorization token");
        }

        user = userRepository.load(guid);

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
