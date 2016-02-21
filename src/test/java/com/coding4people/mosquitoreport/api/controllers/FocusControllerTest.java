package com.coding4people.mosquitoreport.api.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.mockito.Mock;

import com.coding4people.mosquitoreport.api.WithServer;
import com.coding4people.mosquitoreport.api.controllers.FocusController.FocusQueryInput;
import com.coding4people.mosquitoreport.api.indexers.FocusIndexer;
import com.coding4people.mosquitoreport.api.models.Focus;
import com.coding4people.mosquitoreport.api.repositories.FocusRepository;

public class FocusControllerTest extends WithServer {
    @Mock
    private FocusIndexer focusIndexer;

    @Mock
    private FocusRepository focusRepository;

    @Override
    protected ResourceConfig configure() {
        return super.configure().register(FocusController.class);
    }

    @Test
    public void testQuery() {
        FocusQueryInput data = new FocusQueryInput();
        data.setLatlonnw("36.628611,-121.694152");
        data.setLatlonse("34.628611,-119.694152");

        when(focusIndexer.search(any(), any())).thenReturn("\"cloud search return\"");

        Response response = target().path("/focus/query").request().post(Entity.json(data));

        assertEquals(200, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getHeaderString("Content-type"));

        verify(focusIndexer).search("36.628611,-121.694152", "34.628611,-119.694152");
        assertEquals("\"cloud search return\"", response.readEntity(String.class));
    }
    
    @Test
    public void testDetails() {
        String guid = "00000000-0000-0000-0000-000000000000";
        
        Focus focus = new Focus();
        focus.setGuid(guid);
        
        when(focusRepository.loadOrNotFound(guid)).thenReturn(focus);

        Response response = target().path("/focus/" + guid).request().get();

        assertEquals(200, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getHeaderString("Content-type"));

        verify(focusRepository).loadOrNotFound(guid);
        assertEquals(guid, response.readEntity(Focus.class).getGuid());
    }
}
