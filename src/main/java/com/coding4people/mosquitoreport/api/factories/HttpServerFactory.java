package com.coding4people.mosquitoreport.api.factories;

import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import com.coding4people.mosquitoreport.api.Config;
import com.coding4people.mosquitoreport.api.Env;

public class HttpServerFactory {
    public HttpServer provide() {
        return GrizzlyHttpServerFactory.createHttpServer(URI.create("http://0.0.0.0:" + Env.instance.get("PORT").orElse("9000")),
                new Config().configureAuthentication().configureBinders().configureControllers()
                        .configureExceptionMappers().configureFilters().configureFramework());
    }
}
