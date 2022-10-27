package com.harsh.hotelManagement.repository;

import com.harsh.hotelManagement.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository  //-- need to learn
public interface UserRepository extends MongoRepository<User, String>{
    Optional<List<User>> findUsersByName(String name);
    Optional<User> findUserByUsername(String username);
    void deleteByUsername(String username);
}
