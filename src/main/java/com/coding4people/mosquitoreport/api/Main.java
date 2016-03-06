package com.coding4people.mosquitoreport.api;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.glassfish.grizzly.http.server.HttpServer;

import com.coding4people.mosquitoreport.api.factories.HttpServerFactory;

public class Main {
    private final HttpServer server;
    
    public static HttpServerFactory serverFactory = new HttpServerFactory();
    
    public static HelpFormatter helpFormatter = new HelpFormatter();

    private Main() {
        server = serverFactory.provide();
    }

    public void start() throws IOException {
        server.start();
    }

    public void stop() {
        server.shutdownNow();
    }

    public static void main(String[] args) throws IOException {
        final Options options = new Options().addOption("port", true, "It will also look for $PORT env var.")
                .addOption("dynamodb_endpoint", true, "It will also look for $DYNAMODB_ENDPOINT env var.")
                .addOption("dynamodb_table_prefix", true, "It will also look for $DYNAMODB_TABLE_PREFIX env var.")
                .addOption("bucket_name_picture", true, "It will also look for $BUCKET_NAME_PICTURE env var.")
                .addOption("cloudsearch_domain_prefix", true,
                        "It will also look for $CLOUDSEARCH_DOMAIN_PREFIX env var.");

        try {
            CommandLine cmd = new DefaultParser().parse(options, args);

            if (cmd.hasOption("port")) {
                Env.instance.register("PORT", cmd.getOptionValue("port"));
            }

            if (cmd.hasOption("dynamodb_endpoint")) {
                Env.instance.register("DYNAMODB_ENDPOINT", cmd.getOptionValue("dynamodb_endpoint"));
            }

            if (cmd.hasOption("dynamodb_table_prefix")) {
                Env.instance.register("DYNAMODB_TABLE_PREFIX", cmd.getOptionValue("dynamodb_table_prefix"));
            }

            if (cmd.hasOption("bucket_name_picture")) {
                Env.instance.register("BUCKET_NAME_PICTURE", cmd.getOptionValue("bucket_name_picture"));
            }

            if (cmd.hasOption("cloudsearch_domain_prefix")) {
                Env.instance.register("CLOUDSEARCH_DOMAIN_PREFIX", cmd.getOptionValue("cloudsearch_domain_prefix"));
            }

            new Main().start();
        } catch (ParseException e) {
            helpFormatter.printHelp("java -cp lib com.coding4people.mosquitoreport.api.Main", options);
        }
    }
}
