package com.mywebsurfing.controller;

import com.mywebsurfing.entity.AppUser;
import com.mywebsurfing.repository.AppUserRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

    @Autowired
    private AppUserRepository appUserRepository;

    protected AppUser getOrCreateUser() {
        AppUser user = appUserRepository.findByLogin("user1");
        if (user == null) {
            user = createDefaultUser();
        }
        return user;
    }

    // TODO: do a more appropriate thing
    private AppUser createDefaultUser() {
        AppUser user = new AppUser(null,
                "Default User",
                "user1",
                "secure123",
                "nobody@nowhere.com",
                null);
        appUserRepository.save(user);
        return user;
    }

}
