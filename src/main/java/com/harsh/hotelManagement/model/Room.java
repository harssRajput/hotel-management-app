package com.harsh.hotelManagement.model;

import com.harsh.hotelManagement.model.enums.RoomStatus;
import org.apache.catalina.User;

public class Room {
    private String roomId;
    private RoomStatus status;
    private User rentedTo;

    public Room(String roomId, RoomStatus status) {
        this.roomId = roomId;
        this.status = status;
        this.rentedTo = null;
    }

    //    ------- public -------

    public String getRoomId() {
        return roomId;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public User getRentedTo() {
        return rentedTo;
    }

    public void setRentedTo(User rentedTo) {
        this.rentedTo = rentedTo;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
