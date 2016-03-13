package com.coding4people.mosquitoreport.api.controllers;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.UUID;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.coding4people.mosquitoreport.api.models.Email;
import com.coding4people.mosquitoreport.api.models.PasswordResetToken;
import com.coding4people.mosquitoreport.api.repositories.EmailRepository;
import com.coding4people.mosquitoreport.api.repositories.PasswordResetTokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Path("/reset-password")
public class ResetPasswordController {
    @Inject
    PasswordResetTokenRepository passwordResetTokenRepository;
    
    @Inject
    EmailRepository emailRepository;

    /**
     * @api {POST} /reset-password/request Request a password reset token
     * @apiGroup User
     * 
     * @apiParam {String} TODO
     *
     * @apiParamExample {json} Request-Example:
     *     {
     *      "TODO": "TODO"
     *     }
     * 
     * @apiSuccessExample Success-Headers:
     *     HTTP/1.1 200 OK
     *
     * @apiSuccessExample Success-Response:
     *     {
     *       "TODO": "TODO"
     *     }
     */
    @POST
    @Path("/request")
    @Consumes("application/json")
    @Produces("application/json;charset=UTF-8")
    public ObjectNode request(@Valid RequestResetPasswordInput input) {
        String code = UUID.randomUUID().toString();
        
        Email email = emailRepository.load(input.getEmail());
        
        PasswordResetToken token = new PasswordResetToken();
        token.setEmail(email.getEmail());
        token.setUserguid(email.getUserguid());
        token.setToken(PasswordResetToken.encryptToken(code));
        token.setExpires(Long.toString(LocalDateTime.now().plusDays(5).toEpochSecond(ZoneOffset.UTC)));
        
        String publicToken = Base64.getEncoder().encodeToString((token.getExpires() + "." + code).getBytes()); 
        
        passwordResetTokenRepository.save(token);
        
        // TODO send email and remove debug from the next line
        return new ObjectMapper().createObjectNode().put("status", "sent").put("debug", publicToken);
    }
    
    /**
     * @api {POST} /reset-password Reset user password
     * @apiGroup User
     * 
     * @apiParam {String} TODO
     *
     * @apiParamExample {json} Request-Example:
     *     {
     *      "TODO": "TODO"
     *     }
     * 
     * @apiSuccessExample Success-Headers:
     *     HTTP/1.1 200 OK
     *
     * @apiSuccessExample Success-Response:
     *     {
     *       "TODO": "TODO"
     *     }
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json;charset=UTF-8")
    public ObjectNode reset(@Valid ResetPasswordInput input) {
        String expires;
        String rawToken;
        
        try {
            String decodedPublicToken = new String(Base64.getDecoder().decode(input.getToken()));
            expires = decodedPublicToken.substring(0, decodedPublicToken.indexOf("."));
            rawToken = decodedPublicToken.substring(decodedPublicToken.indexOf(".") + 1);
        } catch (Throwable t) {
            throw new BadRequestException("Invalid token");
        }
        
        PasswordResetToken token = passwordResetTokenRepository.load(input.getEmail(), expires);
        
        if (token == null || !PasswordResetToken.checkToken(rawToken, token.getToken())) {
            throw new BadRequestException("Invalid token");
        }
        
        if (LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) > Long.valueOf(expires)) {
            throw new BadRequestException("This token is expired");
        }
        
        Email email = emailRepository.load(token.getEmail());

        if (!email.getUserguid().equals(token.getUserguid())) {
            throw new BadRequestException("Invalid token");
        }
        
        //TODO delete token
        email.setPassword(Email.encryptPassword(input.getNewPassword()));
        emailRepository.save(email);
        
        return new ObjectMapper().createObjectNode().put("status", "reset");
    }

    public static class RequestResetPasswordInput {
        @NotNull
        private String email;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
    
    public static class ResetPasswordInput {
        @NotNull
        private String email;
        
        @NotNull
        private String token;
        
        @NotEmpty
        @NotNull
        @Length(min = 6)
        private String newPassword;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }
}
