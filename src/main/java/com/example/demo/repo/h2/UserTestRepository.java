package com.example.demo.repo.h2;


import com.example.demo.entity.h2.Usertest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTestRepository
        extends JpaRepository<Usertest, Integer> { }