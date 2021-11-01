package com.mywebsurfing.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class HomePageServiceImpl implements HomePageService {

    @Override
    public List<String> getRealms() {
        return Arrays.asList("Learning languages", "Event announcements", "Social, etc.");
    }

    @Override
    public List<String> getMediaTypes() {
        return Arrays.asList("Text", "Audio", "Video", "Messenger channels", "Messenger chats");
    }

    @Override
    public List<String> getManagedAreas() {
        return Arrays.asList("Bookmarks", "Random reminders", "Spaced repetition");
    }

}
