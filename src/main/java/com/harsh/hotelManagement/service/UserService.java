package com.harsh.hotelManagement.service;

import com.harsh.hotelManagement.model.AddUserResponseVo;
import com.harsh.hotelManagement.model.User;
import com.harsh.hotelManagement.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private Logger log = LoggerFactory.getLogger(UserService.class);

//    private List<User> users = new ArrayList<User>(Arrays.asList(
//            new User("Ram", 30, "ram001"),
//            new User("Shyam", 30, "shyam002")
//    ));

//    private int getCustomerIdxByName(String name){
//        if(name.equals("")) return -1;
//
//        int customerIdx=-1;
//
//        for(int i = 0; i< users.size(); i++)
//            if(users.get(i).getName().equals(name)) customerIdx = i;
//
//        return customerIdx;
//    }

//  ------------ public ----------------

    public Optional<User> findUserByUserName(String username){
        try {
            log.info("method: UserService.findUserByUserName -- got request with username {}", username);
            return userRepository.findUserByUsername(username);
        }catch (Exception e){
            log.error("method: UserService.findUserByUserName -- Something Went Wrong --> username: {}, exception: {} ", username, e);
            return null;
        }
    }

    public Optional<List<User>> findUserByName(String name){
        try{
            log.info("method: UserService.findUserByName -- got request with name {}", name);
            return userRepository.findUsersByName(name);
        }catch (Exception e){
            log.error("method: UserService.findUserByName -- Something Went Wrong --> name: {}, exception: {} ", name, e);
            return null;
        }
    }

    public boolean deleteUserByUserName(String username){
        try{
            log.info("method: UserService.deleteUserByUserName -- got delete user request with username {}", username);
            userRepository.deleteByUsername(username);
            return true;
        }catch (Exception e){
            log.error("method: UserService.deleteUserByUserName -- Something Went Wrong --> username: {}, exception: {} ", username, e);
            return false;
        }
    }

    public AddUserResponseVo addUser(User user){
        AddUserResponseVo addUserResponseVo = null;
        try{
            log.info("method: UserService.addUser -- got add user request with user {}", user);
            if(userRepository.findUserByUsername(user.getUsername()).isPresent())
                addUserResponseVo = new AddUserResponseVo("username already taken", null);
            else
                addUserResponseVo = new AddUserResponseVo("user added successfully", userRepository.save(user));
        }catch(Exception e){
            log.error("method: UserService.addUser -- Something Went Wrong --> user {}", user);
        }
        return addUserResponseVo;
    }
}
