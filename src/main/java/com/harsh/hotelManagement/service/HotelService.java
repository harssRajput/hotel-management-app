package com.harsh.hotelManagement.service;

import com.harsh.hotelManagement.model.Hotel;
import com.harsh.hotelManagement.model.Room;
import com.harsh.hotelManagement.model.enums.HotelStatus;
import com.harsh.hotelManagement.model.enums.RoomStatus;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
            new Hotel("usaHotel", "usa", HotelStatus.OPEN),
            new Hotel("arabHotel", "arab", HotelStatus.CLOSED)
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

    private boolean isValid(Hotel hotel){
        return (hotel.getName().isEmpty()
                || hotel.getLocation().isEmpty()
                || hotel.getAllRooms().size()==0
                || hotel.getAllRooms().stream().filter(
                (room) -> room.getStatus().equals(RoomStatus.VACANT)
        ).count() != (long)hotel.getAvailableRoomCnt()
        ) ? false : true;
    }

//    ---------- public -----------
    public Hotel getHotelByName(String name){
        Hotel hotel = null;
        int idx = getHotelIdxByName(name);
        return idx == -1 ? null : hotels.get(idx);
    }

    public List<Hotel> getHotelByLocation(String location){
        List<Hotel> hotelListRes;

        hotelListRes = hotels.stream().filter((hotel) -> hotel.getLocation().equals(location)).collect(Collectors.toList());

        return hotelListRes;
    }

    public List<Hotel> getHotelByAvailability(){
        List<Hotel> availableHotels = new ArrayList<>();
        for (Hotel hotel : hotels)
            if(hotel.getStatus().equals(HotelStatus.OPEN)) availableHotels.add(hotel);

        return availableHotels;
    }

    public List<Hotel> getHotelByRoomAvailability(){
        List<Hotel> availableHotels = new ArrayList<>();
        List<Hotel> openedHotels = getHotelByAvailability();

        for (Hotel hotel : openedHotels)
            if(hotel.getAvailableRoomCnt()>0) availableHotels.add(hotel);

        return availableHotels;
    }

    public boolean addHotel(Hotel hotel){
        boolean isAdded = true;//assume hotel is good to get added

        if(!isValid(hotel)) isAdded = false;//validation failed
        else hotels.add(hotel);

        return isAdded;
    }

    public List<Room> getRoomByHotelName(String hotelName){

        for (Hotel hotel : hotels)
            if(hotel.getName().equals(hotelName)) return hotel.getAllRooms();

        return new ArrayList<>();
    }
}
