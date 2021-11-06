package com.mywebsurfing.controller;

import com.mywebsurfing.dto.DataImportReport;
import com.mywebsurfing.entity.AppUser;
import com.mywebsurfing.repository.AppUserRepository;
import com.mywebsurfing.service.DataImportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataImportController {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private DataImportService dataImportService;

    // this will work is the CSV file is uploaded via Postman
    @PostMapping("/data/import")
    public DataImportReport importData(@RequestBody String csvFileData) {
        // TODO: do a more appropriate thing to get the current user
        final AppUser user = getOrCreateUser();
        return dataImportService.importData(csvFileData, user);
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

}
