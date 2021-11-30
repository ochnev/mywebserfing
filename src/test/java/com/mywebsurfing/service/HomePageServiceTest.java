package com.mywebsurfing.service;

import com.mywebsurfing.entity.Realm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class HomePageServiceTest extends BaseDataTest {

    @Autowired
    private HomePageService homePageService;

    @Autowired
    private DataImportService dataImportService;

    @Test
    public void test() {
        String line = "IT;Blockchain and Java;https://ethereum.org/en/developers/docs/programming-languages/java/;Ethereum for Java developers";
        dataImportService.importData(line, defaultUser);

        List<Realm> realms = homePageService.getRealms(defaultUser);
        // TODO: make new checks after the service is completed
        assertEquals(1, realms.size());
        assertEquals("IT", realms.get(0).getName());
    }

}
