package com.example.demo.controller;

import com.example.demo.entity.h2.Usertest;
import com.example.demo.repo.h2.UserTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin() // open for all ports
    @RequestMapping("crudh2")
    @RestController
    @EnableTransactionManagement
   public class H2Controller {

        @Autowired
        UserTestRepository userRepository;

        /**
         * Get all the user
         *
         * @return ResponseEntity
         */
        @GetMapping("/user")
        @Transactional("h2TransactionManager")
        public ResponseEntity<Usertest> getUser() {

            Usertest user = new Usertest();
            user.setName("John");
            user.setEmail("john@test.com");
            user.setAge(20);


            try {
                user = userRepository.save(user);
                return new ResponseEntity(user,HttpStatus.OK);

            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    }
