package com.collegeProject.studentEntry.services;

import com.collegeProject.studentEntry.entity.User;
import com.collegeProject.studentEntry.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public boolean saveNewUser(User user) {
        try {
            // below line will encode password before saving
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        }catch (Exception e){
            log.error("Error occurred for {}:",user.getUserName());
            log.info("Error occurred for {}:",user.getUserName());
            log.warn("Error occurred for {}:",user.getUserName());
            log.debug("Error occurred for {}:",user.getUserName());
            log.trace("Error occurred for {}:",user.getUserName());
            return false;
        }
    }

    public List<User> getAllUsers() {
       return userRepository.findAll();
    }

    public User findByUserName(String username){
        return userRepository.findByUserName(username);
    }

    public void createAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }
}
