package com.coding4people.mosquitoreport.api.repositories;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class RepositoryBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(EmailRepository.class).to(EmailRepository.class);
        bind(FocusRepository.class).to(FocusRepository.class);
        bind(PasswordResetTokenRepository.class).to(PasswordResetTokenRepository.class);
        bind(PictureRepository.class).to(PictureRepository.class);
        bind(ThumbsUpRepository.class).to(ThumbsUpRepository.class);
        bind(UserRepository.class).to(UserRepository.class);
    }
}
