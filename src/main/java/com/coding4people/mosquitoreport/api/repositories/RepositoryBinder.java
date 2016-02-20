package com.coding4people.mosquitoreport.api.repositories;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class RepositoryBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(FocusRepository.class).to(FocusRepository.class);
        bind(PictureRepository.class).to(PictureRepository.class);
    }
}
