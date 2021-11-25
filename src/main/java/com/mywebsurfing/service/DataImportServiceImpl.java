package com.mywebsurfing.service;

import com.mywebsurfing.dto.DataImportReport;
import com.mywebsurfing.entity.AppUser;
import com.mywebsurfing.entity.Bookmark;
import com.mywebsurfing.entity.Folder;
import com.mywebsurfing.entity.LinkCollection;
import com.mywebsurfing.entity.Realm;
import com.mywebsurfing.repository.BookmarkRepository;
import com.mywebsurfing.repository.FolderRepository;
import com.mywebsurfing.repository.LinkCollectionRepository;
import com.mywebsurfing.repository.RealmRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class DataImportServiceImpl implements DataImportService {

    @Autowired
    private RealmRepository realmRepository;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private LinkCollectionRepository linkCollectionRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Override
    public DataImportReport importData(String csvFileData, AppUser user) {
        Map<String, Realm> existingRealms = new HashMap<>();
        Map<String, Folder> existingFolders = new HashMap<>();
        Map<String, LinkCollection> existingLinkCollections = new HashMap<>();
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
            if(folderName == null || folderName.isBlank()) {
                // create a link collection
                String url = parts[2];
                String name = parts[3];
                LinkCollection linkCollection = existingLinkCollections.get(url);
                if (linkCollection == null) {
                    linkCollection = linkCollectionRepository.findByUrlAndRealm(url, realm);
                    if (linkCollection == null) {
                        linkCollection = createLinkCollection(name, url, realm);
                    }
                }
                existingLinkCollections.put(url, linkCollection);

            } else {
                // create a folder and a bookmark
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
            }

        });

        return new DataImportReport(existingRealms.size(), existingFolders.size(), existingBookmarks.size());
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

    private LinkCollection createLinkCollection(String name, String url, Realm realm) {
        LinkCollection linkCollection = new LinkCollection(null, name, url, realm);
        linkCollectionRepository.save(linkCollection);
        return linkCollection;
    }

    private Bookmark createBookmark(String url, String title, Folder folder) {
        Bookmark bookmark = new Bookmark(null, url, title, folder);
        bookmarkRepository.save(bookmark);
        return bookmark;
    }

}
