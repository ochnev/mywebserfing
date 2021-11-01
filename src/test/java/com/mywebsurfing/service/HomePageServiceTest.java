package com.mywebsurfing.service;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HomePageServiceTest {

    @Test
    public void test() {
        HomePageService service = new HomePageServiceImpl();
        List<String> realms = service.getRealms();
        List<String> mediaTypes = service.getMediaTypes();
        List<String> managedAreas = service.getManagedAreas();
        assertEquals(Arrays.asList("Learning languages", "Event announcements", "Social, etc."), realms);
        assertEquals(Arrays.asList("Text", "Audio", "Video", "Messenger channels", "Messenger chats"), mediaTypes);
        assertEquals(Arrays.asList("Bookmarks", "Random reminders", "Spaced repetition"), managedAreas);
    }

}
