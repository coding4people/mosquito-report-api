package com.coding4people.mosquitomap.api;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

public class Main {
    public static final String BASE_URI = "http://0.0.0.0:9000/";

    public static ResourceConfig createApp() {
        return new ResourceConfig().property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true)
                .packages("com.coding4people.mosquitomap.api");
    }

    public static void main(String[] args) throws IOException {
        try {
            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), createApp());

            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    server.shutdownNow();
                }
            }));

            server.start();

            Thread.currentThread().join();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
