package com.harsh.hotelManagement.service;

import com.harsh.hotelManagement.model.Hotel;
import com.harsh.hotelManagement.model.Room;
import com.harsh.hotelManagement.model.User;
import com.harsh.hotelManagement.model.AddHotelResponseVo;
import com.harsh.hotelManagement.model.enums.HotelStatus;
import com.harsh.hotelManagement.model.enums.RoomStatus;
import com.harsh.hotelManagement.repository.HotelRepository;
import com.harsh.hotelManagement.repository.UserRepository;
import com.harsh.hotelManagement.validation.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HotelService {

    private Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private Validation hotelValidation;

    //to check user authorisation for withdrawing a room.
    private boolean isAuthorised(User user, Room room){
        return user.getUsername().equals(room.getRentedTo());
    }

//       -------------- public ---------------
    public Optional<Hotel>  getHotelByName(String name){
        try{
            log.info("method: HotelService.getHotelByName searching hotel by name --> {}", name);
            return hotelRepository.findById(name);
        }catch(Exception e){
            log.error("method: HotelService.getHotelByName --> something went wrong in querying db --> {}",e);
            return null;
        }
    }

    public Optional<List<Hotel>> getHotelByLocation(String location){
        try {
            log.info("method: HotelService.getHotelByLocation searching hotel by location --> {}", location);
            return hotelRepository.findHotelsByLocation(location);
        }catch(Exception e){
            log.error("method: HotelService.getHotelByLocation --> something went wrong in querying db --> {}",e);
            return null;
        }
    }

    public Optional<List<Hotel>> getHotelByAvailability(){
        try {
            log.info("method: HotelService.getHotelByAvailability searching hotel by availability");
            return hotelRepository.getHotelsByAvailability();
        }catch(Exception e){
            log.error("method: HotelService.getHotelByAvailability --> something went wrong in querying db --> {}",e);
            return null;
        }
    }

    public AddHotelResponseVo addHotel(Hotel hotel){
        AddHotelResponseVo addHotelResponseVo;

        try{
            log.info("method: HotelService.addHotel adding the new hotel --> {}", hotel);
            if(hotelRepository.findById(hotel.getName()).isPresent()) {
                log.info("method: HotelService.addHotel --> Hotel already exist");
                addHotelResponseVo = new AddHotelResponseVo("Hotel already exist", null);
            }
            else if(!hotelValidation.isNewHotelValid(hotel)) {
                log.info("method: HotelService.addHotel --> hotel data is not valid --> {}", hotel);
                addHotelResponseVo = new AddHotelResponseVo("Data Validation Failed", null);
            }
            else {
                log.info("method: HotelService.addHotel --> hotel added successfully");
                addHotelResponseVo = new AddHotelResponseVo("Hotel added successfully", hotelRepository.save(hotel));
            }
        }catch(Exception e){
            log.error("method: HotelService.addHotel --> something went wrong in querying db --> {}",e);
            addHotelResponseVo = new AddHotelResponseVo("Operation Failed! Internal server error", null);
        }

        return addHotelResponseVo;
    }

    public List<Room> getRoomByHotelName(String hotelName){

        try{
            log.info("method: HotelService.getRoomByHotelName --> getting rooms by hotelname --> {}", hotelName);
            Optional<Hotel> optionalHotel = getHotelByName(hotelName);
            if(optionalHotel.isPresent()) {
                log.info("method: HotelService.getRoomByHotelName --> hotel found");
                return optionalHotel.get().getAllRooms();
            }
            else {
                log.info("method: HotelService.getRoomByHotelName --> hotel not found");
                return new ArrayList<>();
            }
        }catch(Exception e){
            log.error("method: HotelService.getRoomByHotelName --> something went wrong in querying db --> {}",e);
            return null;
        }
    }

    public String bookRoom(String hotelName, String roomId, String userName){
        Hotel hotel = null;
        Room room = null;
        User user = null;

        try{
            //validation
            Optional<User> optionalUser = userRepository.findUserByUsername(userName);
            log.info("method: HotelService.bookRoom --> found optionalUser --> {} by username --> {}", optionalUser, userName);
            if(optionalUser.isPresent()) user = optionalUser.get();
            else return "User not exist";

            Optional<Hotel> optionalHotel = hotelRepository.findById(hotelName);
            log.info("method: HotelService.bookRoom --> found optionalHotel --> {} by hotelname --> {}", optionalHotel, hotelName);
            if(optionalHotel.isPresent()){
                hotel = optionalHotel.get();
                if(hotel.getStatus().equals(HotelStatus.CLOSED))
                    return "Hotel is Closed";
            }
            else
                return "Hotel not exist";

            for (Room r : hotel.getAllRooms())
                if(r.getRoomId().equals(roomId)){
                    room = r;
                    break;
                }
            if(room == null) return "room not exist";
            if(room.getStatus().equals(RoomStatus.BOOKED)) return "Room is not available";

            //room booking logic
            room.setStatus(RoomStatus.BOOKED);
            room.setRentedTo(user.getUsername());
            hotel.setAvailableRoomCnt(hotel.getAvailableRoomCnt() - 1);
            hotelRepository.save(hotel);
            log.info("method: HotelService.bookRoom --> booked room successfully in hotel --> {}", hotel);
            return "Room is booked successfully";
        }catch(Exception e){
            log.error("method: HotelService.bookRoom --> something went wrong --> {}",e);
            return "Something Went Wrong!";
        }
    }

    public String withdrawRoom(String hotelName, String roomId, String userName){
        Hotel hotel = null;
        Room room = null;
        User user = null;

        try{
            //validation
            Optional<User> optionalUser = userRepository.findUserByUsername(userName);
            log.info("method: HotelService.withdrawRoom --> found optionalUser --> {} by username --> {}", optionalUser, userName);
            if(optionalUser.isPresent()) user = optionalUser.get();
            else return "User not exist";

            Optional<Hotel> optionalHotel = hotelRepository.findById(hotelName);
            log.info("method: HotelService.withdrawRoom --> found optionalHotel --> {} by hotelname --> {}", optionalHotel, hotelName);
            if(optionalHotel.isPresent()){
                hotel = optionalHotel.get();
                if(hotel.getStatus().equals(HotelStatus.CLOSED))
                    return "Hotel is Closed";
            }
            else
                return "Hotel not exist";

            for (Room r : hotel.getAllRooms())
                if(r.getRoomId().equals(roomId)){
                    room = r;
                    break;
                }
            if(room == null) return "room not exist";
            if(room.getStatus().equals(RoomStatus.VACANT)) return "Room is already Vacant";

            if(!isAuthorised(user, room)) return "Failed! user not authorised to withdraw room";

            //withdraw room logic
            room.setStatus(RoomStatus.VACANT);
            room.setRentedTo(null);
            hotel.setAvailableRoomCnt(hotel.getAvailableRoomCnt() + 1);
            hotelRepository.save(hotel);
            log.info("method: HotelService.withdrawRoom --> withdrawn room successfully in hotel --> {}", hotel);

            return "Room is withdrawn successfully";
        }catch(Exception e){
            log.error("method: HotelService.withdrawRoom --> something went wrong --> {}",e);
            return "Something Went Wrong!";
        }
    }
}
