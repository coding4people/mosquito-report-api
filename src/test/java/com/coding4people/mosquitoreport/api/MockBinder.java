package com.coding4people.mosquitoreport.api;

import java.lang.reflect.Field;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.mockito.Mock;

import com.coding4people.mosquitoreport.api.models.User;

public class MockBinder extends AbstractBinder {
    private final WithServer test;
    
    private final Field[] fields;
    
    public MockBinder(WithServer test) {
        this.test = test;
        this.fields = test.getClass().getDeclaredFields();
    }
    
    @Override
    protected void configure() {
        bindFactory(new InstanceFactory<User>(test.getCurrentUser())).to(User.class);
        
        for (Field field : fields) {
            if (field.isAnnotationPresent(Mock.class)) {
                try {   
                    field.setAccessible(true);
                    bindFactory(new InstanceFactory<Object>(field.get(test))).to(field.getType());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
