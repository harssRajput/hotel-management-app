package com.harsh.hotelManagement.model;

import com.harsh.hotelManagement.model.enums.RoomStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = "rooms")
public class Room {
    @Id
    private String roomId;
    @Field(targetType = FieldType.STRING)
    private RoomStatus status;
    private String rentedTo;//username of user

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

    public String getRentedTo() {
        return rentedTo;
    }

    public void setRentedTo(String rentedTo) {
        this.rentedTo = rentedTo;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
