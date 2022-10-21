package com.harsh.hotelManagement.model;

import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private String name;
    private String location;
    private List<Room> allRooms;
    private int availableRoomCnt;

//    public Hotel() {}
    public Hotel(String name, String location){
        this.name = name;
        this.location = location;
        this.availableRoomCnt = 0;
        this.allRooms = new ArrayList<Room>();
    }
//    -------- public --------
    public int getAvailableRoomCnt() {
        return availableRoomCnt;
    }

    public void setAvailableRoomCnt(int availableRoomCnt) {
        this.availableRoomCnt = availableRoomCnt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Room> getAllRooms() {
        return allRooms;
    }

    public void setAllRooms(List<Room> allRooms) {
        this.allRooms = allRooms;
    }

}
