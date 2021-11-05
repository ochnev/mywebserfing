package com.mywebsurfing.service;

import com.mywebsurfing.entity.Realm;

import java.util.List;

public interface HomePageService {
    List<Realm> getRealms();
    List<String> getMediaTypes();
    List<String> getManagedAreas();
}
