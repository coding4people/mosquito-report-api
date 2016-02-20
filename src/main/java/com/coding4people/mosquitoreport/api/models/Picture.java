package com.coding4people.mosquitoreport.api.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "picture")
public class Picture {
    @DynamoDBHashKey
    private String focusGuid;
    
    @DynamoDBRangeKey
    private String createAt;

    public String getFocusGuid() {
        return focusGuid;
    }

    public void setFocusGuid(String focusGuid) {
        this.focusGuid = focusGuid;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
