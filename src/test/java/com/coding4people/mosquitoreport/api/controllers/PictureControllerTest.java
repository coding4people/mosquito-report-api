package com.coding4people.mosquitoreport.api.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.mockito.Mock;

import com.coding4people.mosquitoreport.api.WithServer;
import com.coding4people.mosquitoreport.api.buckets.PictureBucket;
import com.coding4people.mosquitoreport.api.models.Focus;
import com.coding4people.mosquitoreport.api.models.Picture;
import com.coding4people.mosquitoreport.api.models.User;
import com.coding4people.mosquitoreport.api.repositories.FocusRepository;
import com.coding4people.mosquitoreport.api.repositories.PictureRepository;

public class PictureControllerTest extends WithServer {
    @Mock
    PictureRepository pictureRepository;
    @Mock
    FocusRepository focusRepository;
    @Mock
    PictureBucket pictureBucket;
    
    User currentUser = new User();
    
    @Override
    protected ResourceConfig configure() {
        return super.configure().register(PictureController.class);
    }
    
    @Override
    protected void configureClient(ClientConfig config) {
        config.register(MultiPartFeature.class);
    }
    
    @Override
    protected User getCurrentUser() {
        return currentUser;
    }

    @Test
    public void testPostDoNotOwn() throws IOException {
        Focus focus = new Focus();
        focus.setAuthoruserguid("88888888-8888-8888-8888-888888888888");
        currentUser.setGuid("99999999-9999-9999-9999-999999999999");
        
        FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
        MultiPart multipart = formDataMultiPart.bodyPart(new FileDataBodyPart("file", Paths.get("pom.xml").toFile()));

        when(focusRepository.loadOrNotFound("00000000-0000-0000-0000-000000000000")).thenReturn(focus);

        Response response = target().path("/picture/focus/00000000-0000-0000-0000-000000000000").request()
                .post(Entity.entity(multipart, multipart.getMediaType()));

        assertEquals(400, response.getStatus());

        verify(focusRepository).loadOrNotFound("00000000-0000-0000-0000-000000000000");

        formDataMultiPart.close();
    }
    
    @Test
    public void testPost() throws IOException {
        Focus focus = new Focus();
        focus.setGuid("00000000-0000-0000-0000-000000000000");
        focus.setAuthoruserguid("88888888-8888-8888-8888-888888888888");
        currentUser.setGuid("88888888-8888-8888-8888-888888888888");
        FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
        MultiPart multipart = formDataMultiPart.bodyPart(new FileDataBodyPart("file", Paths.get("pom.xml").toFile()));

        when(focusRepository.loadOrNotFound("00000000-0000-0000-0000-000000000000")).thenReturn(focus);
        
        Response response = target().path("/picture/focus/00000000-0000-0000-0000-000000000000").request()
                .post(Entity.entity(multipart, multipart.getMediaType()));
        
        assertEquals(200, response.getStatus());
        
        Picture picture = response.readEntity(Picture.class);
        assertNotNull(picture.getGuid());
        assertNotNull(picture.getCreatedat());
        assertEquals("00000000-0000-0000-0000-000000000000", picture.getFocusguid());
        assertEquals("pictures/00000000-0000-0000-0000-000000000000/" + picture.getGuid() + "/pom.xml", picture.getPath());

        verify(focusRepository).loadOrNotFound("00000000-0000-0000-0000-000000000000");
        verify(pictureRepository).save(any(Picture.class));
        verify(pictureBucket).put(eq(picture.getPath()), any(InputStream.class), any(Long.class));

        formDataMultiPart.close();
    }
}
