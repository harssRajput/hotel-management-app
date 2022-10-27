package com.harsh.hotelManagement.controller;

import com.harsh.hotelManagement.model.User;
import com.harsh.hotelManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/username/{username}")
    public User findUserByUserName(@PathVariable("username") String username){
        Optional<User> optionalUser = userService.findUserByUserName(username);
        return optionalUser.isPresent() ? optionalUser.get() : null;
    }

    @GetMapping("/user/name/{name}")
    public List<User> findUserByName(@PathVariable("name") String name){
        Optional<List<User>> optionalUsers = userService.findUserByName(name);
        return optionalUsers.isPresent() ? optionalUsers.get() : null;
    }

    @DeleteMapping("/user")
    public boolean deleteUserByUserName(@RequestParam("name") String name){
        return userService.deleteUserByUserName(name);
    }

    @PostMapping("/user")
    public String addUser(@RequestBody User user){
        return userService.addUser(user);
    }
}

