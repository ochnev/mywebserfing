package com.mywebsurfing.service;

import com.mywebsurfing.entity.Realm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class HomePageServiceTest {

    @Autowired
    private HomePageService homePageService;

    @Test
    public void test() {
        List<Realm> realms = homePageService.getRealms();
        List<String> mediaTypes = homePageService.getMediaTypes();
        List<String> managedAreas = homePageService.getManagedAreas();
        // TODO: make new checks after the service is completed
        assertEquals(0, realms.size());
//        assertEquals("IT", realms.get(0).getName());
        assertEquals(Arrays.asList("Text", "Audio", "Video", "Messenger channels", "Messenger chats"), mediaTypes);
        assertEquals(Arrays.asList("Bookmarks", "Random reminders", "Spaced repetition"), managedAreas);
    }

}
