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

import javax.transaction.Transactional;
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
    @Transactional
    public DataImportReport importData(String csvFileData, AppUser user) {
        Map<String, Realm> existingRealms = new HashMap<>();
        Map<String, Folder> existingFolders = new HashMap<>();
        Map<String, LinkCollection> existingLinkCollections = new HashMap<>();
        Map<String, Bookmark> existingBookmarks = new HashMap<>();

        String[] lines = csvFileData.split("\n");
        DataImportReport report = Arrays.stream(lines)
                .map(line -> importOneLine(line, user, existingRealms, existingFolders, existingLinkCollections, existingBookmarks))
                .reduce(new DataImportReport(), (r1, r2) -> {
            int nRealms = r1.getRealms() + r2.getRealms();
            int nFolders = r1.getFolders() + r2.getFolders();
            int nLinkCollections = r1.getLinkCollections() + r2.getLinkCollections();
            int nBookmarks = r1.getBookmarks() + r2.getBookmarks();
            return new DataImportReport(nRealms, nFolders, nLinkCollections, nBookmarks);
        });
        return report;
    }

    @Override
    @Transactional
    public DataImportReport importOneLine(String dataLine, AppUser user) {
        return importOneLine(dataLine, user, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());
    }

    private DataImportReport importOneLine(String dataLine,
                                          AppUser user,
                                          Map<String, Realm> existingRealms,
                                          Map<String, Folder> existingFolders,
                                          Map<String, LinkCollection> existingLinkCollections,
                                          Map<String, Bookmark> existingBookmarks) {

        String[] parts = dataLine.split(";");
        String realmName = parts[0];
        Realm realm = checkAndCreateRealm(realmName, user, existingRealms);

        String folderName = parts[1];
        if(folderName == null || folderName.isBlank()) {
            // create a link collection
            String url = parts[2];
            String name = parts[3];
            checkAndCreateLinkCollection(name, url, realm, existingLinkCollections);
        } else {
            // create a folder and a bookmark
            Folder folder = checkAndCreateFolder(folderName, realm, existingFolders);
            String url = parts[2];
            String title = parts[3];
            checkAndCreateBookmark(url, title, folder, existingBookmarks);
        }

        return new DataImportReport(existingRealms.size(), existingFolders.size(), existingLinkCollections.size(), existingBookmarks.size());
    }

    private Realm checkAndCreateRealm(String realmName, AppUser user, Map<String, Realm> existingRealms) {
        Realm realm = existingRealms.get(realmName);
        if (realm == null) {
            realm = realmRepository.findByNameAndAppUser(realmName, user);
            if (realm == null) {
                realm = new Realm(null, realmName, user, null);
                realmRepository.save(realm);
            }
            existingRealms.put(realmName, realm);
        }
        return realm;
    }

    private Folder checkAndCreateFolder(String folderName, Realm realm, Map<String, Folder> existingFolders) {
        Folder folder = existingFolders.get(folderName);
        if (folder == null) {
            folder = folderRepository.findByNameAndRealm(folderName, realm);
            if (folder == null) {
                folder = new Folder(null, folderName, realm, null);
                folderRepository.save(folder);
            }
            existingFolders.put(folderName, folder);
        }
        return folder;
    }

    private LinkCollection checkAndCreateLinkCollection(String name, String url, Realm realm, Map<String, LinkCollection> existingLinkCollections) {
        LinkCollection linkCollection = existingLinkCollections.get(url);
        if (linkCollection == null) {
            linkCollection = linkCollectionRepository.findByUrlAndRealm(url, realm);
            if (linkCollection == null) {
                linkCollection = new LinkCollection(null, name, url, realm);
                linkCollectionRepository.save(linkCollection);
            }
            existingLinkCollections.put(url, linkCollection);
        }
        return linkCollection;
    }

    private Bookmark checkAndCreateBookmark(String url, String title, Folder folder, Map<String, Bookmark> existingBookmarks) {
        Bookmark bookmark = existingBookmarks.get(url);
        if (bookmark == null) {
            bookmark = bookmarkRepository.findByUrlAndFolder(url, folder);
            if (bookmark == null) {
                bookmark = new Bookmark(null, url, title, folder);
                bookmarkRepository.save(bookmark);
            }
            existingBookmarks.put(url, bookmark);
        }
        return bookmark;
    }

}
