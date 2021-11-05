package com.mywebsurfing.service;

import com.mywebsurfing.entity.AppUser;
import com.mywebsurfing.entity.Realm;
import com.mywebsurfing.repository.AppUserRepository;
import com.mywebsurfing.repository.BookmarkRepository;
import com.mywebsurfing.repository.FolderRepository;
import com.mywebsurfing.repository.RealmRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class HomePageServiceImpl implements HomePageService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private RealmRepository realmRepository;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Override
    public List<Realm> getRealms() {
        AppUser user = appUserRepository.findByLogin("user1"); // TODO: implement real logic
        return realmRepository.findByAppUser(user);
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
