package com.collegeProject.studentEntry.controller;

import com.collegeProject.studentEntry.entity.Student;
import com.collegeProject.studentEntry.entity.User;
import com.collegeProject.studentEntry.services.StudentServices;
import com.collegeProject.studentEntry.services.UserServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/studentData")
public class StudentController {

    @Autowired
    private StudentServices studentServices;

    @Autowired
    private UserServices userServices;

    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        //Now username is coming from http (postMan)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName(); // storing username in a variable
        User user = userServices.findByUserName(userName);
            List<Student> student = user.getStudent();
            if (student != null && !student.isEmpty()) {
                return new ResponseEntity<>(student, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    private ResponseEntity<?> createStudent(@RequestBody Student student) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            studentServices.addStudent(student,userName);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

//    Get Student by id
    @GetMapping("id/{myId}")
    private ResponseEntity<?> getStudentById(@PathVariable ObjectId myId) {
      // Authenticate the user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
       User user= userServices.findByUserName(userName);
       // get the id of that user student
        List<Student> collect = user.getStudent().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<Student> student = studentServices.findById(myId);
            if (student.isPresent()) {
                return new ResponseEntity<>(student.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("id/{myId}")
    private ResponseEntity<?> deleteStudent(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
         boolean removed=studentServices.deleteById(userName,myId);
         if (removed) {
             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
         }
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{myId}")
    private ResponseEntity<?> updateStudent(@PathVariable ObjectId myId, @RequestBody Student newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user= userServices.findByUserName(userName);
        // get the id of that user student
        List<Student> collect = user.getStudent().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<Student> student = studentServices.findById(myId);
            if (student.isPresent()) {
                 Student old = student.get();
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
                studentServices.saveEntry(old);
                return new ResponseEntity<>(old,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
