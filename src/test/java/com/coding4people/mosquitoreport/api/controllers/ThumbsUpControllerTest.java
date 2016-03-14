package com.coding4people.mosquitoreport.api.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import com.coding4people.mosquitoreport.api.WithServer;
import com.coding4people.mosquitoreport.api.controllers.ThumbsUpController.ThumbsUpPostInput;
import com.coding4people.mosquitoreport.api.indexers.FocusIndexer;
import com.coding4people.mosquitoreport.api.models.Focus;
import com.coding4people.mosquitoreport.api.models.ThumbsUp;
import com.coding4people.mosquitoreport.api.models.User;
import com.coding4people.mosquitoreport.api.repositories.FocusRepository;
import com.coding4people.mosquitoreport.api.repositories.ThumbsUpRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ThumbsUpControllerTest extends WithServer {
    @Mock
    ThumbsUpRepository thumbsUpRepository;

    @Mock
    FocusRepository focusRepository;

    @Mock
    FocusIndexer focusIndexer;

    User currentUser = new User();

    @Override
    protected ResourceConfig configure() {
        return super.configure().register(ThumbsUpController.class);
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Test
    public void testThumbsUpDuplicated() {
        ThumbsUpPostInput input = new ThumbsUpPostInput();
        input.setFocusguid("00000000-0000-0000-0000-000000000000");
        currentUser.setGuid("99999999-9999-9999-9999-999999999999");

        when(thumbsUpRepository.load("00000000-0000-0000-0000-000000000000", "99999999-9999-9999-9999-999999999999"))
                .thenReturn(new ThumbsUp());

        Response response = target().path("/thumbsup").request().post(Entity.json(input));

        assertEquals(400, response.getStatus());

        verify(thumbsUpRepository).load("00000000-0000-0000-0000-000000000000", "99999999-9999-9999-9999-999999999999");
    }

    @Test
    public void testThumbsUp() {
        ThumbsUpPostInput input = new ThumbsUpPostInput();
        input.setFocusguid("00000000-0000-0000-0000-000000000000");
        currentUser.setGuid("99999999-9999-9999-9999-999999999999");
        
        Focus focus = new Focus();
        focus.setGuid("00000000-0000-0000-0000-000000000000");
        focus.setThumbsup(99);

        when(thumbsUpRepository.load("00000000-0000-0000-0000-000000000000", "99999999-9999-9999-9999-999999999999"))
                .thenReturn(null);
        
        when(focusRepository.loadOrNotFound("00000000-0000-0000-0000-000000000000")).thenReturn(focus);

        Response response = target().path("/thumbsup").request().post(Entity.json(input));

        assertEquals(200, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getHeaderString("Content-type"));
        
        ArgumentCaptor<ThumbsUp> thumbsUpCaptor = ArgumentCaptor.forClass(ThumbsUp.class);

        verify(thumbsUpRepository).load("00000000-0000-0000-0000-000000000000", "99999999-9999-9999-9999-999999999999");
        verify(thumbsUpRepository).save(thumbsUpCaptor.capture());
        verify(focusRepository).save(focus);
        verify(focusIndexer).index(focus);
        
        ThumbsUp thumbsUp = thumbsUpCaptor.getValue();
        assertEquals("00000000-0000-0000-0000-000000000000", thumbsUp.getFocusguid());
        assertEquals("99999999-9999-9999-9999-999999999999", thumbsUp.getUserguid());

        ObjectNode json = response.readEntity(ObjectNode.class);
        assertEquals(100, json.get("thumbsup").asInt());
    }
}
