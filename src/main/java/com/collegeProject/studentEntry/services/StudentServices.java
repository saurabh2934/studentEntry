package com.collegeProject.studentEntry.services;


import com.collegeProject.studentEntry.entity.Student;
import com.collegeProject.studentEntry.entity.User;
import com.collegeProject.studentEntry.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class StudentServices {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserServices userServices;

    public List<Student> sendAllStudent() {
        return studentRepository.findAll();
    }

    @Transactional // it did not store anything in db if one situation failed,
    // It allows to store only that situation if every thing is ok.
    public void addStudent(Student student, String username) {
        try {
            User user = userServices.findByUserName(username);
            student.setDate(LocalDateTime.now());
            Student newStudent = studentRepository.save(student);
            user.getStudent().add(newStudent);
            // find Id from userDb
            userServices.saveUser(user);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("An error occurred while saving student",e);
        }
    }

    public void saveEntry(Student student) {
        studentRepository.save(student);
    }


    @Transactional
    public boolean deleteById(String userName, ObjectId myId) {
        boolean removed;
        try {
            User user = userServices.findByUserName(userName);
          removed = user.getStudent().removeIf(x -> x.getId().equals(myId));
            if (removed) {
                userServices.saveUser(user); // using this statement our user data has been deleted
                studentRepository.deleteById(myId); // using this statement student data has been deleted
            }
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("An error occurred while deleting student",e);
        }
        return removed;
    }


    public Optional<Student> findById(ObjectId myId) {
       return studentRepository.findById(myId);
    }
}
