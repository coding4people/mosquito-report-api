package com.coding4people.mosquitoreport.api.filters;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.Test;

public class CORSFilterTest {
    @Test
    public void testFilter() throws IOException {
        ContainerResponseContext response = mock(ContainerResponseContext.class);
        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
        when(response.getHeaders()).thenReturn(headers);
        
        new CORSFilter().filter(null, response);
        
        assertNotNull(headers.get("Access-Control-Allow-Origin"));
        assertNotNull(headers.get("Access-Control-Allow-Headers"));
        assertNotNull(headers.get("Access-Control-Allow-Credentials"));
        assertNotNull(headers.get("Access-Control-Allow-Methods"));
    }
}
