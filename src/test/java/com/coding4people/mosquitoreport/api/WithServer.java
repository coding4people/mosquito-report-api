package com.coding4people.mosquitoreport.api;

import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.jackson.internal.FilteringJacksonJaxbJsonProvider;
import org.glassfish.jersey.jackson.internal.JacksonFilteringFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

public class WithServer extends JerseyTest {
    @Override
    protected ResourceConfig configure() {
        return new ResourceConfig().property(CommonProperties.FEATURE_AUTO_DISCOVERY_DISABLE, true)
                .register(JacksonFilteringFeature.class).register(FilteringJacksonJaxbJsonProvider.class);
    }
}
