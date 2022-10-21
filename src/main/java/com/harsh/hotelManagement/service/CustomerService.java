package com.harsh.hotelManagement.service;

import com.harsh.hotelManagement.model.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CustomerService {

    private List<Customer> customers= new ArrayList<Customer>(Arrays.asList(
            new Customer("Ram", 30),
            new Customer("Shyam", 30)
    ));

    private int getCustomerIdxByName(String name){
        if(name.equals("")) return -1;

        int customerIdx=-1;

        for(int i=0; i< customers.size(); i++)
            if(customers.get(i).getName().equals(name)) customerIdx = i;

        return customerIdx;
    }

//  ------------ public ----------------
    public Customer findCustomer(String name){
        Customer customer = null;
        int idx = getCustomerIdxByName(name);
        return idx == -1 ? null : customers.get(idx);
    }

    public boolean deleteCustomer(String name){
        int idx = getCustomerIdxByName(name);
        boolean isDeleted = true; //can be used later when some use-cases may enforce to not delete customer from db.
        if(idx != -1) customers.remove(idx);
        return isDeleted;
    }

    public boolean addCustomer(Customer customer){
        boolean isAdded = true;
        customers.add(customer);
        return isAdded;
    }
}
