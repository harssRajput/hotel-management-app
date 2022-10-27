package com.harsh.hotelManagement.validation;

import com.harsh.hotelManagement.model.Hotel;
import com.harsh.hotelManagement.model.Room;
import com.harsh.hotelManagement.model.enums.HotelStatus;
import com.harsh.hotelManagement.model.enums.RoomStatus;
import com.harsh.hotelManagement.repository.UserRepository;
import com.harsh.hotelManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Validation {
    @Autowired
    UserRepository userRepository;

    //to check new-hotel rooms are in valid format or not
    private boolean isUsernameExist(String username){
        if(username == null
                || username.trim().isEmpty()
                || !userRepository.findUserByUsername(username).isPresent()
        ) return false;
        return true;
    }

    private boolean isRoomsValid(Hotel hotel){
        boolean isValid = false;
        for(Room room : hotel.getAllRooms()){
            if(room.getStatus() != null
                    && (room.getStatus().equals(RoomStatus.VACANT) && room.getRentedTo()==null)
                    || (room.getStatus().equals(RoomStatus.BOOKED) && isUsernameExist(room.getRentedTo()))
            ) isValid = true;
            else{
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    public boolean isNewHotelValid(Hotel hotel){
        return (hotel.getName()==null
                || hotel.getName().trim().isEmpty()
                || hotel.getLocation()==null
                || hotel.getLocation().trim().isEmpty()
                || hotel.getStatus()==null
                || !(hotel.getStatus().equals(HotelStatus.OPEN) || hotel.getStatus().equals(HotelStatus.CLOSED))
                || !isRoomsValid(hotel)
                || hotel.getAllRooms().size()==0
                || hotel.getAllRooms().stream().filter(
                (room) -> room.getStatus().equals(RoomStatus.VACANT)
        ).count() != (long)hotel.getAvailableRoomCnt()
        ) ? false : true;
    }
}
