package com.coding4people.mosquitoreport.api.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnore;

@DynamoDBTable(tableName = "focus")
public class Focus implements Searchable {
    @DynamoDBHashKey
    private String guid;
    
    @DynamoDBRangeKey
    private String createdat;
    
    @DynamoDBAttribute
    private String latlon;
    
    @DynamoDBAttribute
    private String notes;
    
    @DynamoDBAttribute
    // There is a bug on DynamoDB local parsing numbers ):
    private String thumbsup;
    
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

    public String getThumbsup() {
        return thumbsup;
    }

    public void setThumbsup(String thumbsup) {
        this.thumbsup = thumbsup;
    }
    
    @JsonIgnore
    public Focus thumbsup() {
        thumbsup = new Integer(Integer.parseInt(thumbsup) + 1).toString();
        
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
    public String getSearchId() {
        // We use this format to make it easy to index all data using AWS Console
        return guid + "_" + createdat;
    }
}
