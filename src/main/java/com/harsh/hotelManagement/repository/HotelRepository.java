package com.harsh.hotelManagement.repository;

import com.harsh.hotelManagement.model.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository  //-- need to learn
public interface HotelRepository extends MongoRepository<Hotel, String> {
    @Query("{$and:[{status: OPEN},{availableRoomCnt:{ $gt: 0 }}]}")
    Optional<List<Hotel>> getHotelsByAvailability();

    Optional<List<Hotel>> findHotelsByLocation(String location);
}
