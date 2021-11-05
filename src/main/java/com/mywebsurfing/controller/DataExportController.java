package com.mywebsurfing.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataExportController {

    @PostMapping("/data/export")
    public String exportData(@RequestParam String userName,
                             @RequestParam String password) {
        return "data export"; // TODO
    }

}
