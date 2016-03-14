package com.coding4people.mosquitoreport.api.controllers;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.coding4people.mosquitoreport.api.indexers.FocusIndexer;
import com.coding4people.mosquitoreport.api.models.Focus;
import com.coding4people.mosquitoreport.api.repositories.FocusRepository;

@Path("/focus")
public class FocusController {
    @Inject FocusIndexer focusIndexer;
    @Inject FocusRepository focusRepository;

    /**
     * @api {post} /focus/query Search focus inside a squared area
     * @apiGroup Focus
     * 
     * @apiParam {String} latlonnw Northwestern latitude and longitude
     * @apiParam {String} latlonse Southeastern latitude and longitude
     *
     * @apiParamExample {json} Request-Example:
     *     {
     *       "latlonnw": "-22.6993761,-47.4095964",
     *       "latlonse": "-24.6993761,-45.4095964"
     *     }
     * 
     * @apiSuccessExample Success-Headers:
     *     HTTP/1.1 200 OK
     *
     * @apiSuccessExample Success-Response:
     *     [
     *       {
     *         "latlon": "-23.6993761,-46.4095964",
     *         "authoruserguid": "f68079be-2c23-41dd-9ca2-0cc6aa368c5a",
     *         "guid": "c6e376cb-2e3b-4721-b22b-0c07eb06a835",
     *         "thumbsup": "0",
     *         "createdat": "1457646582199"
     *       },
     *       {
     *         "latlon": "-23.697866,-46.408891",
     *         "authoruserguid": "f68079be-2c23-41dd-9ca2-0cc6aa368c5a",
     *         "guid": "c329f1ea-78b6-42a6-ac7e-b7959a0ef8f8",
     *         "thumbsup": "0",
     *         "createdat": "1457646640229"
     *       }
     *   ]
     */
    @POST
    @Path("/query")
    @Consumes("application/json")
    @Produces("application/json;charset=UTF-8")
    public Object query(@Valid FocusQueryInput input) {
        return focusIndexer.search(input.getLatlonnw(), input.getLatlonse());
    }
    
    /**
     * @api {post} /focus/query-center Search focuses near by a single point
     * @apiGroup Focus
     * 
     * @apiParam {String} latlon Latitude and longitude
     *
     * @apiParamExample {json} Request-Example:
     *     {
     *       "latlon": "-23.6993761,-46.4095964",
     *     }
     * 
     * @apiSuccessExample Success-Headers:
     *     HTTP/1.1 200 OK
     *
     * @apiSuccessExample Success-Response:
     *     [
     *       {
     *         "latlon": "-23.6993761,-46.4095964",
     *         "authoruserguid": "f68079be-2c23-41dd-9ca2-0cc6aa368c5a",
     *         "guid": "c6e376cb-2e3b-4721-b22b-0c07eb06a835",
     *         "thumbsup": "0",
     *         "createdat": "1457646582199"
     *       },
     *       {
     *         "latlon": "-23.697866,-46.408891",
     *         "authoruserguid": "f68079be-2c23-41dd-9ca2-0cc6aa368c5a",
     *         "guid": "c329f1ea-78b6-42a6-ac7e-b7959a0ef8f8",
     *         "thumbsup": "0",
     *         "createdat": "1457646640229"
     *       }
     *   ]
     */
    @POST
    @Path("/query-center")
    @Consumes("application/json")
    @Produces("application/json;charset=UTF-8")
    public Object queryCenter(@Valid FocusCenterInput input) {
        return focusIndexer.searchCenter(input.getLatlon());
    }
    
    /**
     * @api {GET} /:guid Show focus details
     * @apiGroup Focus
     * 
     * @apiParam {String} guid Focus guid
     *
     * @apiParamExample {json} Request-Example:
     *     /focus/d35d4485-9dc1-4f44-880e-f94bf773c420
     * 
     * @apiSuccessExample Success-Headers:
     *     HTTP/1.1 200 OK
     *
     * @apiSuccessExample Success-Response:
     *     {
     *       "guid": "d35d4485-9dc1-4f44-880e-f94bf773c420",
     *       "createdat": "1457998498349",
     *       "latlon": "-23.6993761,-46.4095964",
     *       "notes": "Lots of tires near 2th Street",
     *       "thumbsup": "0",
     *       "authoruserguid": "f68079be-2c23-41dd-9ca2-0cc6aa368c5a"
     *     }
     */
    @GET
    @Path("{guid}")
    @Produces("application/json;charset=UTF-8")
    public Focus details(@PathParam("guid") String guid) {
        return focusRepository.loadOrNotFound(guid);
    }

    public static class FocusQueryInput {
        @NotNull
        private String latlonnw;
        
        @NotNull
        private String latlonse;

        public String getLatlonnw() {
            return latlonnw;
        }

        public void setLatlonnw(String latlonnw) {
            this.latlonnw = latlonnw;
        }

        public String getLatlonse() {
            return latlonse;
        }

        public void setLatlonse(String latlonse) {
            this.latlonse = latlonse;
        }
    }
    
    public static class FocusCenterInput {
        @NotNull
        private String latlon;

        public String getLatlon() {
            return latlon;
        }

        public void setLatlon(String latlon) {
            this.latlon = latlon;
        }
    }
}
