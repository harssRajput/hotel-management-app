package com.harsh.hotelManagement.service;

import com.harsh.hotelManagement.model.Hotel;
import com.harsh.hotelManagement.model.Room;
import com.harsh.hotelManagement.model.User;
import com.harsh.hotelManagement.model.enums.AddHotelResponseVo;
import com.harsh.hotelManagement.model.enums.HotelStatus;
import com.harsh.hotelManagement.model.enums.RoomStatus;
import com.harsh.hotelManagement.repository.HotelRepository;
import com.harsh.hotelManagement.repository.UserRepository;
import com.harsh.hotelManagement.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class HotelService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private Validation hotelValidation;

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

    private List<Hotel> getHotelByHotelAvailability(){
        List<Hotel> availableHotels = new ArrayList<>();
        for (Hotel hotel : hotels)
            if(hotel.getStatus().equals(HotelStatus.OPEN)) availableHotels.add(hotel);

        return availableHotels;
    }

    //to check user authorisation for withdrawing a room.
    private boolean isAuthorised(User user, Room room){
        return user.getUsername().equals(room.getRentedTo());
    }

//    ---------- public -----------
    public Optional<Hotel>  getHotelByName(String name){
          return hotelRepository.findById(name);
//        Hotel hotel = null;
//        int idx = getHotelIdxByName(name);
//        return idx == -1 ? null : hotels.get(idx);
    }

    public Optional<List<Hotel>> getHotelByLocation(String location){
        return hotelRepository.findHotelsByLocation(location);
    }

    public Optional<List<Hotel>> getHotelByAvailability(){
        return hotelRepository.getHotelsByAvailability();
    }

    public AddHotelResponseVo addHotel(Hotel hotel){
        AddHotelResponseVo addHotelResponseVo;

        if(hotelRepository.findById(hotel.getName()).isPresent())
            addHotelResponseVo = new AddHotelResponseVo("Hotel already exist", null);
        else if(!hotelValidation.isNewHotelValid(hotel))
            addHotelResponseVo = new AddHotelResponseVo("Data Validation Failed", null);
        else
            addHotelResponseVo = new AddHotelResponseVo("Hotel added successfully", hotelRepository.save(hotel));

        return addHotelResponseVo;
    }

    public List<Room> getRoomByHotelName(String hotelName){

        Optional<Hotel> optionalHotel = getHotelByName(hotelName);
        if(optionalHotel.isPresent())
            return optionalHotel.get().getAllRooms();
        else
            return new ArrayList<>();

//        for (Hotel hotel : hotels)
//            if(hotel.getName().equals(hotelName)) return hotel.getAllRooms();
//
//        return new ArrayList<>();
    }

    public String bookRoom(String hotelName, String roomId, String userName){
        Hotel hotel = null;
        Room room = null;
        User user = null;

        //validation
        Optional<User> optionalUser = userRepository.findUserByUsername(userName);
        if(optionalUser.isPresent()){
            user = optionalUser.get();
        }else{
            return "User not exist";
        }

        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelName);
        if(optionalHotel.isPresent()){
            hotel = optionalHotel.get();
            if(hotel.getStatus().equals(HotelStatus.CLOSED))
                return "Hotel is Closed";
        }
        else
            return "Hotel not exist";

//        for (Hotel h : hotels)
//            if(h.getName().equals(hotelName)){
//                hotel = h;
//                break;
//            }
//        if(hotel == null) return "Hotel not exist";
//        if(hotel.getStatus().equals(HotelStatus.CLOSED)) return "Hotel is Closed";

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

        return "Room is booked successfully";
    }

    public String withdrawRoom(String hotelName, String roomId, String userName){
        Hotel hotel = null;
        Room room = null;
        User user = null;

        //validation
        Optional<User> optionalUser = userRepository.findUserByUsername(userName);
        if(optionalUser.isPresent()){
            user = optionalUser.get();
        }else{
            return "User not exist";
        }

        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelName);
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

        return "Room is withdrawn successfully";
    }
}
