package com.coding4people.mosquitoreport.api.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "picture")
public class Picture {
    @DynamoDBHashKey
    private String focusguid;
    
    @DynamoDBRangeKey
    private String createdat;
    
    @DynamoDBAttribute
    private String guid;
    
    @DynamoDBAttribute
    private String filename;

    public String getFocusguid() {
        return focusguid;
    }

    public void setFocusGuid(String focusguid) {
        this.focusguid = focusguid;
    }

    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
    
    public String getPath() {
        return "pictures/" + focusguid + "/" + getGuid() + "/" + (filename == null ? "picture" : filename);
    }
    
    public void setPath(String string) {
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
