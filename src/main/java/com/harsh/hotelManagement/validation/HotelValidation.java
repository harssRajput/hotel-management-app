package com.harsh.hotelManagement.validation;

import com.harsh.hotelManagement.model.Hotel;
import com.harsh.hotelManagement.model.enums.RoomStatus;
import org.springframework.stereotype.Component;

@Component
public class HotelValidation {
    public boolean isNewHotelValid(Hotel hotel){
        return (hotel.getName().isEmpty()
                || hotel.getLocation().isEmpty()
                || hotel.getAllRooms().size()==0
                || hotel.getAllRooms().stream().filter(
                (room) -> room.getStatus().equals(RoomStatus.VACANT)
        ).count() != (long)hotel.getAvailableRoomCnt()
        ) ? false : true;
    }
}
