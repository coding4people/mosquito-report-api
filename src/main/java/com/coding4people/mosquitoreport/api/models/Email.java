package com.coding4people.mosquitoreport.api.models;

import org.mindrot.jbcrypt.BCrypt;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "email")
public class Email {
    @DynamoDBHashKey
    private String email;
    
    @DynamoDBAttribute
    private String userguid;
    
    @DynamoDBAttribute
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserguid() {
        return userguid;
    }

    public void setUserguid(String userguid) {
        this.userguid = userguid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public static String encryptPassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt(12));
    }
    
    public static boolean checkPassword(String rawPassword, String password) {
        if(null == password || !password.startsWith("$2a$")) {
            throw new IllegalArgumentException("Invalid hash provided for comparison");
        }
        
        return BCrypt.checkpw(rawPassword, password);
    }
}
