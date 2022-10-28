package com.harsh.hotelManagement.service;

import com.harsh.hotelManagement.model.AddUserResponseVo;
import com.harsh.hotelManagement.model.User;
import com.harsh.hotelManagement.repository.UserRepository;
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
        return userRepository.findUserByUsername(username);
    }

    public Optional<List<User>> findUserByName(String name){
        return userRepository.findUsersByName(name);
    }

    public boolean deleteUserByUserName(String username){
        userRepository.deleteByUsername(username);
        return true;
    }

    public AddUserResponseVo addUser(User user){
        AddUserResponseVo addUserResponseVo;
        if(userRepository.findUserByUsername(user.getUsername()).isPresent())
            addUserResponseVo = new AddUserResponseVo("username already taken", null);
        else
            addUserResponseVo = new AddUserResponseVo("user added successfully", userRepository.save(user));

        return addUserResponseVo;
    }
}
