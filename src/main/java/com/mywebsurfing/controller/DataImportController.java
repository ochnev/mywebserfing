package com.mywebsurfing.controller;

import com.mywebsurfing.dto.DataImportReport;
import com.mywebsurfing.entity.AppUser;
import com.mywebsurfing.entity.Bookmark;
import com.mywebsurfing.entity.Folder;
import com.mywebsurfing.entity.Realm;
import com.mywebsurfing.repository.AppUserRepository;
import com.mywebsurfing.repository.BookmarkRepository;
import com.mywebsurfing.repository.FolderRepository;
import com.mywebsurfing.repository.RealmRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DataImportController {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private RealmRepository realmRepository;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    // this will work is the CSV file is uploaded via Postman
    @PostMapping("/data/import")
    public DataImportReport importData(@RequestBody String csvFileData) {
        // TODO: do a more appropriate thing
        final AppUser user = getOrCreateUser();

        Map<String, Realm> existingRealms = new HashMap<>();
        Map<String, Folder> existingFolders = new HashMap<>();
        Map<String, Bookmark> existingBookmarks = new HashMap<>();

        String[] lines = csvFileData.split("\n");
        Arrays.stream(lines).forEach(line -> {
            String[] parts = line.split(";");

            String realmName = parts[0];
            Realm realm = existingRealms.get(realmName);
            if (realm == null) {
                realm = realmRepository.findByNameAndAppUser(realmName, user);
                if (realm == null) {
                    realm = createRealm(realmName, user);
                }
                existingRealms.put(realmName, realm);
            }

            String folderName = parts[1];
            Folder folder = existingFolders.get(folderName);
            if (folder == null) {
                folder = folderRepository.findByNameAndRealm(folderName, realm);
                if (folder == null) {
                    folder = createFolder(folderName, realm);
                }
                existingFolders.put(folderName, folder);
            }

            String url = parts[2];
            String title = parts[3];
            Bookmark bookmark = existingBookmarks.get(url);
            if (bookmark == null) {
                bookmark = bookmarkRepository.findByUrlAndFolder(url, folder);
                if (bookmark == null) {
                    bookmark = createBookmark(url, title, folder);
                }
                existingBookmarks.put(url, bookmark);
            }

        });

        return new DataImportReport(existingRealms.size(), existingFolders.size(), existingBookmarks.size());
    }

    private AppUser getOrCreateUser() {
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

    private Realm createRealm(String name, AppUser user) {
        Realm realm = new Realm(null, name, user, null);
        realmRepository.save(realm);
        return realm;
    }

    private Folder createFolder(String name, Realm realm) {
        Folder folder = new Folder(null, name, realm, null);
        folderRepository.save(folder);
        return folder;
    }

    private Bookmark createBookmark(String url, String title, Folder folder) {
        Bookmark bookmark = new Bookmark(null, url, title, folder);
        bookmarkRepository.save(bookmark);
        return bookmark;
    }

}
