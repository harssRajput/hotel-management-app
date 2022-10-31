package com.harsh.hotelManagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private Logger log = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping({"/", "/home"})
    public String homePage(){
        log.info("method: HomeController.homePage --> Home page accessed");
        return "Home Page";
    }
}
