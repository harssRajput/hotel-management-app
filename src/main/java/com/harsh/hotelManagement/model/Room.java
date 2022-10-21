package com.harsh.hotelManagement.model;

import com.harsh.hotelManagement.model.enums.RoomStatus;

public class Room {
    private String roomId;
    private RoomStatus status;
//    private Hotel hotel;

    public Room(String roomId, RoomStatus status) {
        this.roomId = roomId;
        this.status = status;
//        this.hotel = null;
    }

    //    ------- public -------
    public String getRoomId() {
        return roomId;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
