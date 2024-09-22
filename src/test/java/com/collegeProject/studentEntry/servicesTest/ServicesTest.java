package com.collegeProject.studentEntry.servicesTest;


import com.collegeProject.studentEntry.entity.User;
import com.collegeProject.studentEntry.repository.UserRepository;
import com.collegeProject.studentEntry.services.UserServices;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ServicesTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServices userServices;
    @Disabled
    @ParameterizedTest
    @ArgumentsSource(UserArgumentProvider.class)
    public void findUserTest(User user){
//       User user=userRepository.findByUserName("Radha");
//       assertTrue(!user.getStudent().isEmpty());
        assertTrue(userServices.saveNewUser(user));
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,3,5",
            "20,4,23"
    })
    public void sumTest(int a,int b,int expected){
        assertEquals(expected,a+b,"failed for value "+expected);
    }
}
