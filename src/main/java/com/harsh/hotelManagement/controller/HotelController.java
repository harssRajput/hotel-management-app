package com.harsh.hotelManagement.controller;

import com.harsh.hotelManagement.model.Hotel;
import com.harsh.hotelManagement.model.Room;
import com.harsh.hotelManagement.model.RoomActionRequestVo;
import com.harsh.hotelManagement.model.AddHotelResponseVo;
import com.harsh.hotelManagement.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/hotel/name/{name}")
    public Hotel getHotelByName(@PathVariable("name") String name){
        log.info("method: HotelController.getHotelByName --> accessing hotel by hotelName --> {}", name);
        if(name.trim().isEmpty()) return null;

        Optional<Hotel> optionalHotel = hotelService.getHotelByName(name);
        return optionalHotel.isPresent() ? optionalHotel.get() : null;
    }

    @GetMapping("/hotel/location/{location}")
    public List<Hotel> getHotelByLocation(@PathVariable("location") String location){
        log.info("method: HotelController.getHotelByLocation --> accessing hotel by location --> {}", location);
        if(location.trim().isEmpty()) return null;

        Optional<List<Hotel>> optionalHotels = hotelService.getHotelByLocation(location);
        return optionalHotels.isPresent() ? optionalHotels.get() : null;
    }

    @GetMapping("/hotel/available")
    public List<Hotel> getHotelByAvailability(){
        log.info("method: HotelController.getHotelByAvailability --> accessing hotel by availability");
        Optional<List<Hotel>> optionalHotels = hotelService.getHotelByAvailability();
        return optionalHotels.isPresent() ? optionalHotels.get() : null;
    }

    @GetMapping("/hotel/room")
    public List<Room> getRoomByHotelName(@RequestParam(name="hname", required = true) String hotelName){
        log.info("method: HotelController.getRoomByHotelName --> accessing rooms by hotelname --> {}", hotelName);
        if(hotelName.trim().isEmpty()) return new ArrayList<>();

        return hotelService.getRoomByHotelName(hotelName);
    }

    @PutMapping("/hotel/book-room")
    public String bookRoom(@RequestBody RoomActionRequestVo roomActionRequestVo){
        log.info("method: HotelController.bookRoom --> book room request roomActionRequestVo --> {}", roomActionRequestVo);
        return hotelService.bookRoom(roomActionRequestVo.getHotelName(), roomActionRequestVo.getRoomId(), roomActionRequestVo.getUserName());
    }

    @PutMapping("/hotel/withdraw-room")
    public String withdrawRoom(@RequestBody RoomActionRequestVo roomActionRequestVo){
        log.info("method: HotelController.withdrawRoom --> withdraw room request roomActionRequestVo --> {}", roomActionRequestVo);
        return hotelService.withdrawRoom(roomActionRequestVo.getHotelName(), roomActionRequestVo.getRoomId(), roomActionRequestVo.getUserName());
    }

    @PostMapping("/hotel")
    public AddHotelResponseVo addHotel(@RequestBody Hotel hotel){
        log.info("method: HotelController.addHotel --> add hotel --> {}", hotel);
        return hotelService.addHotel(hotel);
    }
}
