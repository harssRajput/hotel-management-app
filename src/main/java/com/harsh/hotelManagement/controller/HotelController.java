package com.harsh.hotelManagement.controller;

import com.harsh.hotelManagement.model.Hotel;
import com.harsh.hotelManagement.model.Room;
import com.harsh.hotelManagement.model.RoomActionRequestVo;
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
    public List<Hotel> getHotelByLocation(@PathVariable("location") String location){
        return hotelService.getHotelByLocation(location);
    }

    @GetMapping("/hotel/available")
    public List<Hotel> getHotelByAvailability(){
        return hotelService.getHotelByAvailability();
    }

    @GetMapping("/hotel/room")
    public List<Room> getRoomByHotelName(@RequestParam(name="hname", required = true) String hotelName){
        return hotelService.getRoomByHotelName(hotelName);
    }

    @PutMapping("/hotel/book-room")
    public String bookRoom(@RequestBody RoomActionRequestVo roomActionRequestVo){
        return hotelService.bookRoom(roomActionRequestVo.getHotelName(), roomActionRequestVo.getRoomId(), roomActionRequestVo.getUserName());
    }

    @PutMapping("/hotel/withdraw-room")
    public String withdrawRoom(@RequestBody RoomActionRequestVo roomActionRequestVo){
        return hotelService.withdrawRoom(roomActionRequestVo.getHotelName(), roomActionRequestVo.getRoomId(), roomActionRequestVo.getUserName());
    }

    @GetMapping("/hotel/room/available")
    public List<Hotel> getHotelByRoomAvailability(){
        return hotelService.getHotelByRoomAvailability();
    }

    @PostMapping("/hotel")
    public boolean addHotel(@RequestBody Hotel hotel){
        return hotelService.addHotel(hotel);
    }

}
