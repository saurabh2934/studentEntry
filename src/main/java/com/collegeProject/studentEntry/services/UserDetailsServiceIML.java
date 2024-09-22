package com.collegeProject.studentEntry.services;

import com.collegeProject.studentEntry.entity.User;
import com.collegeProject.studentEntry.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceIML implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user= userRepository.findByUserName(username); // getting the user from DB
        if(user!=null){ // if user find then we will do something
           return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
        }
       throw  new UsernameNotFoundException("User Not Found: "+username); // if user not found then we return an exception with message
    }
}
