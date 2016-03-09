package com.coding4people.mosquitoreport.api;

import com.coding4people.mosquitoreport.api.models.User;

public interface BaseTest {
    default User getCurrentUser() {
        return new User();
    }
    
    default Env getEnv() {
        return new Env();
    }
}
