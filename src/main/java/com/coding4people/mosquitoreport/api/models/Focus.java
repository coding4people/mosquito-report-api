package com.coding4people.mosquitoreport.api.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnore;

@DynamoDBTable(tableName = "focus")
public class Focus implements Searchable {
    @DynamoDBHashKey
    private String guid;
    
    @DynamoDBAttribute
    private String createdat;
    
    @DynamoDBAttribute
    private String latlon;
    
    @DynamoDBAttribute
    private String notes;
    
    @DynamoDBAttribute
    private Integer thumbsup;
    
    @DynamoDBAttribute
    private String authoruserguid;
    
    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
    
    public String getCreatedat() {
        return createdat;
    }
    
    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }
    
    public String getLatlon() {
        return latlon;
    }

    public void setLatlon(String latLon) {
        this.latlon = latLon;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getThumbsup() {
        return thumbsup;
    }

    public void setThumbsup(Integer thumbsup) {
        this.thumbsup = thumbsup;
    }
    
    @JsonIgnore
    @DynamoDBIgnore
    public Focus thumbsup() {
        thumbsup += 1;
        
        return this;
    }

    public String getAuthoruserguid() {
        return authoruserguid;
    }

    public void setAuthoruserguid(String authoruserguid) {
        this.authoruserguid = authoruserguid;
    }

    @Override
    @JsonIgnore
    @DynamoDBIgnore
    public String getSearchId() {
        return guid;
    }
}
