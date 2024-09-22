package com.collegeProject.studentEntry.repository;

import com.collegeProject.studentEntry.entity.Student;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<Student, ObjectId> {
}
