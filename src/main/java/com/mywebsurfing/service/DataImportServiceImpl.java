package com.mywebsurfing.service;

import com.mywebsurfing.dto.DataImportReport;
import com.mywebsurfing.entity.AppUser;
import com.mywebsurfing.entity.Bookmark;
import com.mywebsurfing.entity.Folder;
import com.mywebsurfing.entity.TopBookmark;
import com.mywebsurfing.entity.Realm;
import com.mywebsurfing.repository.BookmarkRepository;
import com.mywebsurfing.repository.FolderRepository;
import com.mywebsurfing.repository.TopBookmarkRepository;
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
    private TopBookmarkRepository topBookmarkRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Override
    @Transactional
    public DataImportReport importData(String csvFileData, AppUser user) {
        Map<String, Realm> existingRealms = new HashMap<>();
        Map<String, Folder> existingFolders = new HashMap<>();
        Map<String, TopBookmark> existingTopBookmarks = new HashMap<>();
        Map<String, Bookmark> existingBookmarks = new HashMap<>();

        String[] lines = csvFileData.split("\n");
        DataImportReport report = Arrays.stream(lines)
                .map(line -> importOneLine(line, user, existingRealms, existingFolders, existingTopBookmarks, existingBookmarks))
                .reduce(new DataImportReport(), (r1, r2) -> {
            int nRealms = r1.getRealms() + r2.getRealms();
            int nFolders = r1.getFolders() + r2.getFolders();
            int nTopBookmarks = r1.getTopBookmarks() + r2.getTopBookmarks();
            int nBookmarks = r1.getBookmarks() + r2.getBookmarks();
            return new DataImportReport(nRealms, nFolders, nTopBookmarks, nBookmarks);
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
                                          Map<String, TopBookmark> existingTopBookmarks,
                                          Map<String, Bookmark> existingBookmarks) {

        String[] parts = dataLine.split(";");
        String realmName = parts[0];
        Realm realm = checkAndCreateRealm(realmName, user, existingRealms);

        String folderName = parts[1];
        if(folderName == null || folderName.isBlank()) {
            // create a top bookmark directly under the realm
            String url = parts[2];
            String name = parts[3];
            checkAndCreateTopBookmark(name, url, realm, existingTopBookmarks);
        } else {
            // create a folder and a bookmark
            Folder folder = checkAndCreateFolder(folderName, realm, existingFolders);
            String url = parts[2];
            String title = parts[3];
            checkAndCreateBookmark(url, title, folder, existingBookmarks);
        }

        return new DataImportReport(existingRealms.size(), existingFolders.size(), existingTopBookmarks.size(), existingBookmarks.size());
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

    private TopBookmark checkAndCreateTopBookmark(String name, String url, Realm realm, Map<String, TopBookmark> existingTopBookmarks) {
        TopBookmark topBookmark = existingTopBookmarks.get(url);
        if (topBookmark == null) {
            topBookmark = topBookmarkRepository.findByUrlAndRealm(url, realm);
            if (topBookmark == null) {
                topBookmark = new TopBookmark(null, name, url, realm);
                topBookmarkRepository.save(topBookmark);
            }
            existingTopBookmarks.put(url, topBookmark);
        }
        return topBookmark;
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
