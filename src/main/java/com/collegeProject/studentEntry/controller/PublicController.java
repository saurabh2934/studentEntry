package com.collegeProject.studentEntry.controller;


import com.collegeProject.studentEntry.entity.User;
import com.collegeProject.studentEntry.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// This class will unauthenticated
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserServices userServices;

    @PostMapping
    public ResponseEntity<List<User>> createUser(@RequestBody User user) {
        userServices.saveNewUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}