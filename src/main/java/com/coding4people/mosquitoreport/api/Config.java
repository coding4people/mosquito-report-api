package com.coding4people.mosquitoreport.api;

import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.coding4people.mosquitoreport.api.buckets.BucketBinder;
import com.coding4people.mosquitoreport.api.controllers.AuthEmailController;
import com.coding4people.mosquitoreport.api.controllers.AuthFacebookController;
import com.coding4people.mosquitoreport.api.controllers.FocusController;
import com.coding4people.mosquitoreport.api.controllers.HealthCheckController;
import com.coding4people.mosquitoreport.api.controllers.PictureController;
import com.coding4people.mosquitoreport.api.controllers.PostFocusController;
import com.coding4people.mosquitoreport.api.controllers.ProfileController;
import com.coding4people.mosquitoreport.api.controllers.ResetPasswordController;
import com.coding4people.mosquitoreport.api.controllers.SignUpController;
import com.coding4people.mosquitoreport.api.controllers.ThumbsUpController;
import com.coding4people.mosquitoreport.api.exceptionmappers.BadRequestExceptionMapper;
import com.coding4people.mosquitoreport.api.exceptionmappers.ForbiddenExceptionMapper;
import com.coding4people.mosquitoreport.api.exceptionmappers.InternalServerErrorExceptionMapper;
import com.coding4people.mosquitoreport.api.exceptionmappers.JsonMappingExceptionMapper;
import com.coding4people.mosquitoreport.api.exceptionmappers.JsonParseExceptionMapper;
import com.coding4people.mosquitoreport.api.exceptionmappers.NotFoundExceptionMapper;
import com.coding4people.mosquitoreport.api.exceptionmappers.ValidationExceptionMapper;
import com.coding4people.mosquitoreport.api.factories.FactoryBinder;
import com.coding4people.mosquitoreport.api.filters.CORSFilter;
import com.coding4people.mosquitoreport.api.indexers.IndexerBinder;
import com.coding4people.mosquitoreport.api.repositories.RepositoryBinder;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public class Config extends ResourceConfig {
    public Config configureControllers() {
        register(AuthEmailController.class);
        register(AuthFacebookController.class);
        register(FocusController.class);
        register(HealthCheckController.class);
        register(PictureController.class);
        register(PostFocusController.class);
        register(ProfileController.class);
        register(ResetPasswordController.class);
        register(SignUpController.class);
        register(ThumbsUpController.class);

        return this;
    }

    public Config configureExceptionMappers() {
        register(BadRequestExceptionMapper.class);
        register(ForbiddenExceptionMapper.class);
        register(InternalServerErrorExceptionMapper.class);
        register(JsonMappingExceptionMapper.class);
        register(JsonParseExceptionMapper.class);
        register(NotFoundExceptionMapper.class);
        register(ValidationExceptionMapper.class);

        return this;
    }

    public Config configureFilters() {
        register(CORSFilter.class);

        return this;
    }

    public Config configureBinders() {
        register(new BucketBinder());
        register(new FactoryBinder());
        register(new IndexerBinder());
        register(new RepositoryBinder());

        return this;
    }

    public Config configureFramework() {
        property(CommonProperties.FEATURE_AUTO_DISCOVERY_DISABLE, true);
        register(JacksonJaxbJsonProvider.class, MessageBodyReader.class, MessageBodyWriter.class);
        register(MultiPartFeature.class);

        return this;
    }
}
