package com.harsh.hotelManagement.controller;

import com.harsh.hotelManagement.model.Hotel;
import com.harsh.hotelManagement.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @RequestMapping("/hotel")
    public Hotel getHotelByName(String name){
        return hotelService.getHotelByName(name);
    }
}
