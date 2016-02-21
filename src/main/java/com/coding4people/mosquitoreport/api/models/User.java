package com.coding4people.mosquitoreport.api.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "user")
public class User {
    @DynamoDBHashKey
    private String guid;
    
    @DynamoDBAttribute
    private String firstname;
    
    @DynamoDBAttribute
    private String lastname;
    
    @DynamoDBAttribute
    private String location;
    
    @DynamoDBAttribute
    private String facebookurl;
    
    @DynamoDBAttribute
    private String twitter;
    
    @DynamoDBAttribute
    private String profilepictureguid;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFacebookurl() {
        return facebookurl;
    }

    public void setFacebookurl(String facebookurl) {
        this.facebookurl = facebookurl;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getProfilepictureguid() {
        return profilepictureguid;
    }

    public void setProfilepictureguid(String profilepictureguid) {
        this.profilepictureguid = profilepictureguid;
    }
}
