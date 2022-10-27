package com.harsh.hotelManagement.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.Generated;

@Document(collection = "users")
public class User {
    @Id
    String username;//suppose to be passed unique or get error.
    String name;
    int age;

    public User() {
    }

    public User(String name, int age, String username) {
        this.name = name;
        this.age = age;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
