package com.harsh.hotelManagement.model;

import com.harsh.hotelManagement.model.enums.HotelStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "hotels")
public class Hotel {

    @Id
    private String name;
    private String location;
    private List<Room> allRooms;
    private int availableRoomCnt;
    @Field(targetType = FieldType.STRING)
    private HotelStatus status;

    public Hotel() {}
    public Hotel(String name, String location, HotelStatus status){
        this.name = name;
        this.location = location;
        this.availableRoomCnt = 0;
        this.allRooms = new ArrayList<Room>();
        this.status = status; //HotelStatus.OPEN;
    }

    //    -------- public --------
    public HotelStatus getStatus() {
        return status;
    }

    public void setStatus(HotelStatus status) {
        this.status = status;
    }

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
