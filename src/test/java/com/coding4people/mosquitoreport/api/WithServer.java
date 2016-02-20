package com.coding4people.mosquitoreport.api;

import java.lang.reflect.Field;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

abstract public class WithServer extends JerseyTest {
    @Override
    protected ResourceConfig configure() {
        final WithServer t = this;
        final Field[] fields = getClass().getDeclaredFields();

        MockitoAnnotations.initMocks(t);

        return Main.commonConfig()
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        for (Field field : fields) {
                            if (field.isAnnotationPresent(Mock.class)) {
                                try {
                                    field.setAccessible(true);
                                    bindFactory(new InstanceFactory<Object>(field.get(t))).to(field.getType());
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
    }
}
