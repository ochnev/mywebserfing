package com.mywebsurfing.service;

import com.mywebsurfing.entity.AppUser;
import com.mywebsurfing.entity.Realm;

import java.util.List;

public interface HomePageService {
    List<Realm> getRealms(AppUser user);
}
