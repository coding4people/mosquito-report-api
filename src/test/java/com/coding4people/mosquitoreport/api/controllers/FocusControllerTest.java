package com.coding4people.mosquitoreport.api.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.mockito.Mock;

import com.coding4people.mosquitoreport.api.WithServer;
import com.coding4people.mosquitoreport.api.controllers.FocusController.FocusPostInput;
import com.coding4people.mosquitoreport.api.controllers.FocusController.FocusQueryInput;
import com.coding4people.mosquitoreport.api.indexers.FocusIndexer;
import com.coding4people.mosquitoreport.api.models.Focus;
import com.coding4people.mosquitoreport.api.repositories.FocusRepository;

public class FocusControllerTest extends WithServer {
    @Mock
    private FocusRepository focusRepository;
    @Mock
    private FocusIndexer focusIndexer;

    @Override
    protected ResourceConfig configure() {
        return super.configure().register(FocusController.class);
    }

    @Test
    public void testPost() {
        FocusPostInput data = new FocusPostInput();
        data.setLatlon("35.628611,-120.694152");

        Response response = target().path("/focus").request().post(Entity.json(data));

        assertEquals(200, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getHeaderString("Content-type"));

        verify(focusRepository).save(any(Focus.class));
        verify(focusIndexer).index(any(Focus.class));
        Focus focus = response.readEntity(Focus.class);

        assertNotNull(focus.getGuid());
        assertEquals("35.628611,-120.694152", focus.getLatlon());
        assertNotNull(focus.getCreateat());
    }

    @Test
    public void testQuery() {
        FocusQueryInput data = new FocusQueryInput();
        data.setLatlonnw("36.628611,-121.694152");
        data.setLatlonse("34.628611,-119.694152");

        when(focusIndexer.search(any(), any())).thenReturn("\"cloud search return\"");
        
        Response response = target().path("/focus/query")
                .request().post(Entity.json(data));

        assertEquals(200, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getHeaderString("Content-type"));

        verify(focusIndexer).search("36.628611,-121.694152", "34.628611,-119.694152");
        assertEquals("\"cloud search return\"", response.readEntity(String.class));
    }
}
