package com.mywebsurfing.service;

import com.mywebsurfing.entity.AppUser;
import com.mywebsurfing.entity.Bookmark;
import com.mywebsurfing.entity.Folder;
import com.mywebsurfing.entity.Realm;
import com.mywebsurfing.repository.AppUserRepository;
import com.mywebsurfing.repository.BookmarkRepository;
import com.mywebsurfing.repository.FolderRepository;
import com.mywebsurfing.repository.RealmRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class DataImportServiceTest {

    @Autowired
    private DataImportService dataImportService;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private RealmRepository realmRepository;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    private AppUser defaultUser = new AppUser(null,
            "Default User",
            "user1",
            "secure123",
            "nobody@nowhere.com",
            null);

    @BeforeEach
    private void setup() {
        appUserRepository.save(defaultUser);
    }

    @Test
    void testImportData() {
        String csvData = "IT;Blockchain;https://www.npmjs.com/package/create-eth-app;Create Eth App\n" +
                "IT;Blockchain and Java;https://ethereum.org/en/developers/docs/programming-languages/java/;ETHEREUM FOR JAVA DEVELOPERS\n" +
                "Languages;German;https://www.youtube.com/watch?v=RuGmc662HDg&list=PLF9mJC4RrjIhS4MMm0x72-qWEn1LRvPuW;German for beginners - A1";
        dataImportService.importData(csvData, defaultUser);

        List<Realm> realms = realmRepository.findAll();
        List<Realm> realmsForUser = realmRepository.findByAppUser(defaultUser);
        List<Folder> folders = folderRepository.findAll();
        List<Bookmark> bookmarks = bookmarkRepository.findAll();

        // TODO: check contents with more details
        assertEquals(2, realms.size());
        assertEquals(2, realmsForUser.size());
        assertEquals(3, folders.size());
        assertEquals(3, bookmarks.size());
    }

    @AfterEach
    private void tearDown() {
        appUserRepository.deleteAll();
        realmRepository.deleteAll();
        folderRepository.deleteAll();
        bookmarkRepository.deleteAll();
    }

}
