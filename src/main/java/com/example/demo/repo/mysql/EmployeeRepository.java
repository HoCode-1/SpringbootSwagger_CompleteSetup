package com.example.demo.repo.mysql;

import com.example.demo.entity.mysql.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
