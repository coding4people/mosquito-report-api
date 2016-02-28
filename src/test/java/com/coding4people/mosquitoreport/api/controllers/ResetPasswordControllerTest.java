package com.coding4people.mosquitoreport.api.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Date;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import com.coding4people.mosquitoreport.api.WithServer;
import com.coding4people.mosquitoreport.api.controllers.ResetPasswordController.RequestResetPasswordInput;
import com.coding4people.mosquitoreport.api.controllers.ResetPasswordController.ResetPasswordInput;
import com.coding4people.mosquitoreport.api.models.Email;
import com.coding4people.mosquitoreport.api.models.PasswordResetToken;
import com.coding4people.mosquitoreport.api.repositories.EmailRepository;
import com.coding4people.mosquitoreport.api.repositories.PasswordResetTokenRepository;

public class ResetPasswordControllerTest extends WithServer {
    @Mock
    PasswordResetTokenRepository passwordResetTokenRepository;
    
    @Mock
    EmailRepository emailRepository;

    @Override
    protected ResourceConfig configure() {
        return super.configure().register(ResetPasswordController.class);
    }

    @Test
    public void testRequest() {
        RequestResetPasswordInput data = new RequestResetPasswordInput();
        data.setEmail("test@test.org");
        
        Email email = new Email();
        email.setEmail("test@test.org");
        email.setUserguid("00000000-0000-0000-0000-000000000000");
        
        when(emailRepository.load("test@test.org")).thenReturn(email);

        Response response = target().path("/reset-password/request").request().post(Entity.json(data));
        
        assertEquals(200, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getHeaderString("Content-type"));

        ArgumentCaptor<PasswordResetToken> tokenCaptor = ArgumentCaptor.forClass(PasswordResetToken.class);
        
        verify(passwordResetTokenRepository).save(tokenCaptor.capture());
        
        PasswordResetToken token = tokenCaptor.getValue();
        
        assertEquals("test@test.org", token.getEmail());
    }
    
    @Test
    public void testNonBase64Encoded() {
        String publicToken = "non-base64-encoded";
        
        ResetPasswordInput data = new ResetPasswordInput();
        data.setEmail("test@test.org");
        data.setToken(publicToken);
        data.setNewRawPassword("123456");
        
        PasswordResetToken token = new PasswordResetToken();
        token.setToken("$2a$12$E1mvo/1OK5kUdGGkbpo2Ceswe24tHU22dj71Zb/YlD90nekZA9WrO");
        token.setEmail("test@test.org");
        token.setExpires("1456699145");
        
        when(passwordResetTokenRepository.load("test@test.org", "1456699145")).thenReturn(token);
        
        Response response = target().path("/reset-password").request().post(Entity.json(data));
        
        assertEquals(400, response.getStatus());
    }
    
    @Test
    public void testInvalidToken() {
        String publicToken = "MTQ1NjY5OTE0NS5pbnZhbGlk";
        
        ResetPasswordInput data = new ResetPasswordInput();
        data.setEmail("test@test.org");
        data.setToken(publicToken);
        data.setNewRawPassword("123456");
        
        PasswordResetToken token = new PasswordResetToken();
        token.setToken("$2a$12$E1mvo/1OK5kUdGGkbpo2Ceswe24tHU22dj71Zb/YlD90nekZA9WrO");
        token.setEmail("test@test.org");
        token.setExpires("1456699145");
        
        when(passwordResetTokenRepository.load("test@test.org", "1456699145")).thenReturn(token);
        
        Response response = target().path("/reset-password").request().post(Entity.json(data));
        
        assertEquals(400, response.getStatus());
    }
    
    @Test
    public void testExpiredToken() {
        String expires = Long.toString(LocalDateTime.now().minusDays(10).toEpochSecond(ZoneOffset.UTC));
        String publicToken = Base64.getEncoder().encodeToString((expires + ".cea13f4d-0efc-4c66-9513-0cc36fcb08c9").getBytes());
        
        ResetPasswordInput data = new ResetPasswordInput();
        data.setEmail("test@test.org");
        data.setToken(publicToken);
        data.setNewRawPassword("123456");
        
        PasswordResetToken token = new PasswordResetToken();
        token.setToken("$2a$12$E1mvo/1OK5kUdGGkbpo2Ceswe24tHU22dj71Zb/YlD90nekZA9WrO");
        token.setEmail("test@test.org");
        token.setExpires(expires);
        
        when(passwordResetTokenRepository.load("test@test.org", expires)).thenReturn(token);
        
        Response response = target().path("/reset-password").request().post(Entity.json(data));
        
        assertEquals(400, response.getStatus());
    }
    
    @Test
    public void testInvalidGuid() {
        String expires = String.valueOf(new Date().getTime());
        String publicToken = Base64.getEncoder().encodeToString((expires + ".cea13f4d-0efc-4c66-9513-0cc36fcb08c9").getBytes());
        
        ResetPasswordInput data = new ResetPasswordInput();
        data.setEmail("test@test.org");
        data.setToken(publicToken);
        data.setNewRawPassword("123456");
        
        PasswordResetToken token = new PasswordResetToken();
        token.setToken("$2a$12$E1mvo/1OK5kUdGGkbpo2Ceswe24tHU22dj71Zb/YlD90nekZA9WrO");
        token.setEmail("test@test.org");
        token.setExpires(expires);
        token.setUserguid("00000000-0000-0000-0000-000000000000");
        
        Email email = new Email();
        email.setEmail("test@test.org");
        email.setUserguid("88888888-8888-8888-8888-888888888888");
        
        when(passwordResetTokenRepository.load("test@test.org", expires)).thenReturn(token);
        when(emailRepository.load("test@test.org")).thenReturn(email);
        
        Response response = target().path("/reset-password").request().post(Entity.json(data));
        
        assertEquals(400, response.getStatus());
    }
    
    @Test
    public void testReset() {
        String expires = String.valueOf(new Date().getTime());
        String publicToken = Base64.getEncoder().encodeToString((expires + ".cea13f4d-0efc-4c66-9513-0cc36fcb08c9").getBytes());
        
        ResetPasswordInput data = new ResetPasswordInput();
        data.setEmail("test@test.org");
        data.setToken(publicToken);
        data.setNewRawPassword("123456");
        
        PasswordResetToken token = new PasswordResetToken();
        token.setToken("$2a$12$E1mvo/1OK5kUdGGkbpo2Ceswe24tHU22dj71Zb/YlD90nekZA9WrO");
        token.setEmail("test@test.org");
        token.setExpires(expires);
        token.setUserguid("00000000-0000-0000-0000-000000000000");
        
        Email email = new Email();
        email.setEmail("test@test.org");
        email.setUserguid("00000000-0000-0000-0000-000000000000");
        
        when(passwordResetTokenRepository.load("test@test.org", expires)).thenReturn(token);
        when(emailRepository.load("test@test.org")).thenReturn(email);
        
        Response response = target().path("/reset-password").request().post(Entity.json(data));
        
        assertEquals(200, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getHeaderString("Content-type"));
        
        verify(emailRepository).save(email);
    }
}
