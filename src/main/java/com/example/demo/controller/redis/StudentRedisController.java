package com.example.demo.controller.redis;

import com.example.demo.entity.redis.Student;
import com.example.demo.repo.redis.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("redis")
@RestController
public class StudentRedisController {

    @Autowired
    StudentRepository studentRepository;

    @PostMapping("/createstudent")
    public ResponseEntity<Student> newStudent(){
        Student student = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
        studentRepository.save(student);
        return new ResponseEntity(studentRepository.findById("Eng2015001"),HttpStatus.OK);
    }

}
