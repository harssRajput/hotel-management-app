package com.harsh.hotelManagement.controller;

import com.harsh.hotelManagement.model.User;
import com.harsh.hotelManagement.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping("/customer")
    public User findCustomerByName(@RequestParam("name") String name){
        return customerService.findCustomer(name);
    }

    @DeleteMapping("/customer")
    public boolean deleteCustomerByName(@RequestParam("name") String name){
        return customerService.deleteCustomer(name);
    }

    @PostMapping("/customer")
    public boolean addCustomer(@RequestBody User user){
        return customerService.addCustomer(user);
    }
}
