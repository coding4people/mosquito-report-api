package com.coding4people.mosquitoreport.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.apache.commons.cli.HelpFormatter;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.Test;

import com.coding4people.mosquitoreport.api.factories.HttpServerFactory;

public class MainTest {

    @Test
    public void testMain() throws IOException, InterruptedException {
        HttpServerFactory serverFactory = mock(HttpServerFactory.class);
        HttpServer server = mock(HttpServer.class);

        when(serverFactory.provide()).thenReturn(server);

        HttpServerFactory defaultServerFactory = Main.serverFactory;
        Main.serverFactory = serverFactory;

        Main.main(new String[] { "-port", "9876", "-dynamodb_endpoint", "dynamodb_endpoint_value",
                "-dynamodb_table_prefix", "dynamodb_table_prefix_value", "-bucket_name_picture",
                "bucket_name_picture_value", "-cloudsearch_domain_prefix", "cloudsearch_domain_prefix_value", });

        Main.serverFactory = defaultServerFactory;

        verify(server).start();

        assertEquals("9876", Env.instance.get("PORT"));
        assertEquals("dynamodb_endpoint_value", Env.instance.get("DYNAMODB_ENDPOINT"));
        assertEquals("dynamodb_table_prefix_value", Env.instance.get("DYNAMODB_TABLE_PREFIX"));
        assertEquals("bucket_name_picture_value", Env.instance.get("BUCKET_NAME_PICTURE"));
        assertEquals("cloudsearch_domain_prefix_value", Env.instance.get("CLOUDSEARCH_DOMAIN_PREFIX"));
    }

    @Test
    public void testParseException() throws IOException, InterruptedException {
        HelpFormatter helpFormatter = mock(HelpFormatter.class);

        HelpFormatter defaultHelpFormatter = Main.helpFormatter;
        Main.helpFormatter = helpFormatter;

        Main.main(new String[] { "-invalid_option" });

        Main.helpFormatter = defaultHelpFormatter;

        verify(helpFormatter).printHelp(any(), any());
    }
}
