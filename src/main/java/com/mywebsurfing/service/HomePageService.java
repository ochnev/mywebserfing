package com.mywebsurfing.service;

import java.util.List;

public interface HomePageService {
    List<String> getRealms();
    List<String> getMediaTypes();
    List<String> getManagedAreas();
}
