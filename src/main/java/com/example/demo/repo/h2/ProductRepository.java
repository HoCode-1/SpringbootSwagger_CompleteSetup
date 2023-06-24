package com.example.demo.repo.h2;

import com.example.demo.entity.h2.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository
        extends JpaRepository<Product, Integer> { }