package com.coding4people.mosquitoreport.api.models;

import org.mindrot.jbcrypt.BCrypt;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "passwordresettoken")
public class PasswordResetToken {
    @DynamoDBHashKey
    private String email;
    
    @DynamoDBRangeKey
    private String expires;
    
    @DynamoDBAttribute
    private String token;
    
    @DynamoDBAttribute
    private String userguid;
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }

    public String getUserguid() {
        return userguid;
    }

    public void setUserguid(String userguid) {
        this.userguid = userguid;
    }
    
    public static String encryptToken(String rawToken) {
        return BCrypt.hashpw(rawToken, BCrypt.gensalt(12));
    }
    
    public static boolean checkToken(String rawToken, String token) {
        if(null == token || !token.startsWith("$2a$")) {
            throw new IllegalArgumentException("Invalid hash provided for comparison");
        }
        
        return BCrypt.checkpw(rawToken, token);
    }
}
