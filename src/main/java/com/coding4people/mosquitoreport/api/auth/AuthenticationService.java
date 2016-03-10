package com.coding4people.mosquitoreport.api.auth;

import java.util.Base64;

import javax.inject.Inject;
import javax.ws.rs.ForbiddenException;

import org.mindrot.jbcrypt.BCrypt;

import com.coding4people.mosquitoreport.api.Env;

public class AuthenticationService {
    @Inject
    Env env;

    public String generateToken(String userGuid) {
        return Base64.getEncoder().encodeToString(
                (userGuid + '.' + BCrypt.hashpw(env.get("SECRET").orElse("") + userGuid, BCrypt.gensalt(12)))
                        .getBytes());
    }

    public String identify(String token) {
        final String raw;

        try {
            raw = new String(Base64.getDecoder().decode(token));
        } catch (Throwable t) {
            throw new ForbiddenException("Invalid authorization token");
        }

        if (raw.length() <= 37) {
            throw new ForbiddenException("Invalid authorization token");
        }

        String guid = raw.substring(0, 36);
        String hash = raw.substring(37);

        boolean valid;

        try {
            valid = BCrypt.checkpw(env.get("SECRET").orElse("") + guid, hash);
        } catch (IllegalArgumentException e) {
            throw new ForbiddenException("Invalid authorization token");
        }

        if (!valid) {
            throw new ForbiddenException("Invalid authorization token");
        }

        return guid;
    }
}
