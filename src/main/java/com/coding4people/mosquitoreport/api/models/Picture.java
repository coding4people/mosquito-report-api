package com.coding4people.mosquitoreport.api.models;

public class Picture {
    private String focusGuid;
    
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
