package com.mywebsurfing.controller;

import com.mywebsurfing.dto.DataImportReport;
import com.mywebsurfing.entity.AppUser;
import com.mywebsurfing.service.DataImportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataImportController extends BaseController {

    @Autowired
    private DataImportService dataImportService;

    // this will work if the CSV file is uploaded via Postman
    @PostMapping("/data/import")
    public DataImportReport importData(@RequestBody String csvFileData) {
        // TODO: do a more appropriate thing to get the current user
        final AppUser user = getOrCreateUser();
        return dataImportService.importData(csvFileData, user);
    }

    @PostMapping("/data/import/oneline")
    public DataImportReport importOneLine(@RequestBody String dataLine) {
        // TODO: do a more appropriate thing to get the current user
        final AppUser user = getOrCreateUser();
        return dataImportService.importOneLine(dataLine, user);
    }

}
