package com.harsh.hotelManagement.controller;

import com.harsh.hotelManagement.model.Hotel;
import com.harsh.hotelManagement.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/hotel/name/{name}")
    public Hotel getHotelByName(@PathVariable("name") String name){
        return hotelService.getHotelByName(name);
    }

    @GetMapping("/hotel/location/{location}")
    public Hotel getHotelByLocation(@PathVariable("location") String location){
        return hotelService.getHotelByLocation(location);
    }

    @GetMapping("/hotel/available")
    public List<Hotel> getHotelByAvailability(){
        return hotelService.getHotelByAvailability();
    }

}
