package com.collegeProject.studentEntry;


import com.collegeProject.studentEntry.entity.User;
import com.collegeProject.studentEntry.repository.UserRepository;
import com.collegeProject.studentEntry.services.UserDetailsServiceIML;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


public class UserDetailsServiceIMPTest {

    @InjectMocks
    private UserDetailsServiceIML userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }



    @Disabled
    @Test
    public void loadUserByUsernameTest() {
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("Mikku").password("uiidd").roles(new ArrayList<>()).build());
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername("Mikku1");
            assertNotNull(userDetails,"result "+userDetails.getUsername()+" is null");
        }catch (UsernameNotFoundException e) {
            e.printStackTrace();
        }

    }

}
