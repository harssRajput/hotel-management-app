package com.harsh.hotelManagement.service;

import com.harsh.hotelManagement.model.Hotel;
import com.harsh.hotelManagement.model.Room;
import com.harsh.hotelManagement.model.enums.RoomStatus;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class HotelService {

    private List<Room> roomH1 = new ArrayList<Room>( Arrays.asList(
            new Room("usaRoom1", RoomStatus.VACANT),
            new Room("usaRoom2", RoomStatus.VACANT),
            new Room("usaRoom3", RoomStatus.VACANT)
    ));
    private List<Room> roomH2 = new ArrayList<Room>( Arrays.asList(
            new Room("arabRoom1", RoomStatus.VACANT),
            new Room("arabRoom2", RoomStatus.VACANT)
    ));

    private List<Hotel> hotels = new ArrayList<Hotel>(Arrays.asList(
            new Hotel("usaHotel", "usa"),
            new Hotel("arabHotel", "arab")
    ));

    public HotelService(){
//        roomH1.forEach(room -> room.setHotel(hotels.get(0)));
        hotels.get(0).setAllRooms(roomH1);
        hotels.get(0).setAvailableRoomCnt(roomH1.size());

//        roomH2.forEach(room -> room.setHotel(hotels.get(1)));
        hotels.get(1).setAllRooms(roomH2);
        hotels.get(1).setAvailableRoomCnt(roomH2.size());
    }

    private int getHotelIdxByName(String name){
        if(name.equals("")) return -1;

        int hotelIdx=-1;

        for(int i=0; i< hotels.size(); i++)
            if(hotels.get(i).getName().equals(name)) hotelIdx = i;

        return hotelIdx;
    }

    private int getHotelIdxByLocation(String location){
        if(location.equals("")) return -1;

        int hotelIdx=-1;

        for(int i=0; i< hotels.size(); i++)
            if(hotels.get(i).getLocation().equals(location)) hotelIdx = i;

        return hotelIdx;
    }

//    ---------- public -----------
    public Hotel getHotelByName(String name){
        Hotel hotel = null;
        int idx = getHotelIdxByName(name);
        return idx == -1 ? null : hotels.get(idx);
    }

    public Hotel getHotelByLocation(String location){
        Hotel hotel = null;
        int idx = getHotelIdxByLocation(location);
        return idx == -1 ? null : hotels.get(idx);
    }
}
