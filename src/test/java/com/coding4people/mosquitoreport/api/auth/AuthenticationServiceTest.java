package com.coding4people.mosquitoreport.api.auth;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.ForbiddenException;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;

import com.coding4people.mosquitoreport.api.Env;
import com.coding4people.mosquitoreport.api.WithService;

public class AuthenticationServiceTest extends WithService {
    
    protected Env env = new Env().register("SECRET", "26");
    
    @Override
    protected ResourceConfig configure() {
        return super.configure().register(AuthenticationService.class);
    }
    
    @Override
    public Env getEnv() {
        return env;
    }
    
    @Test
    public void testValid() {
        AuthenticationService service = getService(AuthenticationService.class);
        
        String token = service.generateToken("00000000-0000-0000-0000-000000000000");
        
        assertEquals("00000000-0000-0000-0000-000000000000", service.identify(token));
    }
    
    @Test(expected = ForbiddenException.class)
    public void testNonBase64Token() {
        AuthenticationService service = getService(AuthenticationService.class);
        
        service.identify("invalid token");
    }
    
    @Test(expected = ForbiddenException.class)
    public void testShortToken() {
        AuthenticationService service = getService(AuthenticationService.class);
        
        service.identify("aW52YWxpZCB0b2tlbg==");
    }
    
    @Test(expected = ForbiddenException.class)
    public void testInvalidSalt() {
        AuthenticationService service = getService(AuthenticationService.class);
        
        service.identify("MDAwMDAwMDAtMDAwMC0wMDAwLTAwMDAwMDAwMDAwMC5pbnZhbGlkdG9rZW4=");
    }
    
    @Test(expected = ForbiddenException.class)
    public void testInvalidToken() {
        AuthenticationService service = getService(AuthenticationService.class);
        
        service.identify("MDAwMDAwMDAtMDAwMC0wMDAwLTAwMDAtMDAwMDAwMDAwMDAwLiQyYSQxMiRBb2RLMG56ejBrdGtZampnYjlHVGd1NkJvcC41ZXNlUk9BY2NhM1RJRzRoSVBxM25QcUxpaQ==");
    }
}
