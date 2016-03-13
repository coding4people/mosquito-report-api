package com.coding4people.mosquitoreport.api.controllers;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.coding4people.mosquitoreport.api.buckets.PictureBucket;
import com.coding4people.mosquitoreport.api.models.Focus;
import com.coding4people.mosquitoreport.api.models.Picture;
import com.coding4people.mosquitoreport.api.models.User;
import com.coding4people.mosquitoreport.api.repositories.FocusRepository;
import com.coding4people.mosquitoreport.api.repositories.PictureRepository;

@Path("/picture")
public class PictureController {
    @Inject
    PictureRepository pictureRepository;
    @Inject
    FocusRepository focusRepository;
    @Inject
    PictureBucket pictureBucket;
    @Inject
    User user;

    /**
     * @api {POST} /picture/focus/:guid Attach a picture into a facus
     * @apiGroup Focus
     * 
     * @apiParam {String} guid Focus guid
     *
     * @apiParamExample {json} Request-Example:
     *     {
     *      "TODO": "TODO"
     *     }
     * 
     * @apiSuccessExample Success-Headers:
     *     HTTP/1.1 200 OK
     *
     * @apiSuccessExample Success-Response:
     *     {
     *       "TODO": "TODO"
     *     }
     */
    @POST
    @Consumes({ MediaType.MULTIPART_FORM_DATA })
    @Produces("application/json;charset=UTF-8")
    @Path("/focus/{focusguid}")
    public Picture post(@PathParam("focusguid") String focusguid, @FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDisposition) {
        Focus focus = focusRepository.loadOrNotFound(focusguid);
        
        if (!user.getGuid().equals(focus.getAuthoruserguid())) {
            throw new BadRequestException("User does not own this item");
        }
        
        Picture picture = new Picture();
        picture.setGuid(UUID.randomUUID().toString());
        picture.setFilename(fileDisposition.getFileName());
        picture.setFocusGuid(focusguid);
        picture.setCreatedat(Long.toString(new Date().getTime()));
        
        pictureBucket.put(picture.getPath(), fileInputStream, fileDisposition.getSize());
        pictureRepository.save(picture);

        return picture;
    }
}
