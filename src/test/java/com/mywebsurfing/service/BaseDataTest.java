package com.mywebsurfing.service;

import com.mywebsurfing.entity.AppUser;
import com.mywebsurfing.repository.AppUserRepository;
import com.mywebsurfing.repository.BookmarkRepository;
import com.mywebsurfing.repository.FolderRepository;
import com.mywebsurfing.repository.LinkCollectionRepository;
import com.mywebsurfing.repository.RealmRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public abstract class BaseDataTest {

    @Autowired
    protected AppUserRepository appUserRepository;

    @Autowired
    protected RealmRepository realmRepository;

    @Autowired
    protected FolderRepository folderRepository;

    @Autowired
    protected LinkCollectionRepository linkCollectionRepository;

    @Autowired
    protected BookmarkRepository bookmarkRepository;

    protected final AppUser defaultUser = new AppUser(null,
            "Default User",
            "user1",
            "secure123",
            "nobody@nowhere.com",
            null);

    @BeforeEach
    protected void setup() {
        appUserRepository.save(defaultUser);
    }

    @AfterEach
    protected void tearDown() {
        bookmarkRepository.deleteAll();
        folderRepository.deleteAll();
        linkCollectionRepository.deleteAll();
        realmRepository.deleteAll();
        appUserRepository.deleteAll();
    }

}
