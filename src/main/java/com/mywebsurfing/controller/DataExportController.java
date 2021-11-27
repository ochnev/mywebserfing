package com.mywebsurfing.controller;

import com.mywebsurfing.entity.AppUser;
import com.mywebsurfing.service.DataExportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataExportController extends BaseController{

    @Autowired
    private DataExportService dataExportService;

    @PostMapping("/data/export")
    public String exportData(@RequestParam String userName,
                             @RequestParam String password) {
        // TODO: do a more appropriate thing to get the current user
        final AppUser user = getOrCreateUser();
        return dataExportService.exportData(user);
    }

}
