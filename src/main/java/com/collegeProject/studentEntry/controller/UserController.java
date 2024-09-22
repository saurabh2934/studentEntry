package com.collegeProject.studentEntry.controller;

import com.collegeProject.studentEntry.entity.User;
import com.collegeProject.studentEntry.repository.UserRepository;
import com.collegeProject.studentEntry.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userData")
public class UserController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userServices.getAllUsers();
        if (users != null && !users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //     public ResponseEntity<?> createUser(@RequestBody User user) {
    //        userServices.addUser(user);
    //        return new ResponseEntity<>(HttpStatus.CREATED);
    //    }

    @PutMapping
    public ResponseEntity<List<User>> updateUser(@RequestBody User user) {
        //Now username is coming from http (postMan)
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName(); // storing username in a variable

        User userInDb=userServices.findByUserName(userName);
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userServices.saveNewUser(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<List<User>> deleteUser(){
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        userRepository.deleteByUserName(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
