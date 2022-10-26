package com.harsh.hotelManagement.service;

import com.harsh.hotelManagement.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CustomerService {

    private List<User> users = new ArrayList<User>(Arrays.asList(
            new User("Ram", 30),
            new User("Shyam", 30)
    ));

    private int getCustomerIdxByName(String name){
        if(name.equals("")) return -1;

        int customerIdx=-1;

        for(int i = 0; i< users.size(); i++)
            if(users.get(i).getName().equals(name)) customerIdx = i;

        return customerIdx;
    }

//  ------------ public ----------------

    public User findCustomer(String name){
        User user = null;
        int idx = getCustomerIdxByName(name);
        return idx == -1 ? null : users.get(idx);
    }

    public boolean deleteCustomer(String name){
        int idx = getCustomerIdxByName(name);
        boolean isDeleted = true; //can be used later when some use-cases may enforce to not delete customer from db.
        if(idx != -1) users.remove(idx);
        return isDeleted;
    }

    public boolean addCustomer(User user){
        boolean isAdded = true;
        users.add(user);
        return isAdded;
    }
}
