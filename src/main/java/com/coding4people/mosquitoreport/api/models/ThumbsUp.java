package com.coding4people.mosquitoreport.api.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "thumbsup")
public class ThumbsUp {
    @DynamoDBHashKey
    private String focusguid;
    
    @DynamoDBRangeKey
    private String userguid;

    public String getFocusguid() {
        return focusguid;
    }

    public void setFocusguid(String focusguid) {
        this.focusguid = focusguid;
    }

    public String getUserguid() {
        return userguid;
    }

    public void setUserguid(String userguid) {
        this.userguid = userguid;
    }
}
