package com.coding4people.mosquitoreport.api.repositories;

import javax.inject.Singleton;

import com.coding4people.mosquitoreport.api.models.Picture;

@Singleton
public class PictureRepository extends Repository<Picture> {
    @Override
    protected Class<Picture> getType() {
        return Picture.class;
    }
}
