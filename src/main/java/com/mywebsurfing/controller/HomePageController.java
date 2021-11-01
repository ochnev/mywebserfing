package com.mywebsurfing.controller;

import com.mywebsurfing.service.HomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomePageController {

    @Autowired
    private HomePageService homePageService;

    @GetMapping("/home")
    public List<String> home() {
        return homePageService.getRealms();
    }

}
