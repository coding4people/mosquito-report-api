package com.coding4people.mosquitoreport.api;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.junit.Test;

public class MainTest {
    @Test
    public void testMain() throws IOException, InterruptedException {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    Main.main(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        thread.join(3000);

        String line = "";
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new URL("http://localhost:9000/healthcheck").openStream(), "UTF-8"))) {
            line = reader.readLine();
        }
        
        assertEquals("{\"status\":\"ok\"}", line);

        thread.interrupt();
    }
}
