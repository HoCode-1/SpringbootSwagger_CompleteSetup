package com.example.demo.controller;

//@CrossOrigin(origins = "http://localhost:3000") //open for specific port


import com.example.demo.entity.h2.Usertest;
import com.example.demo.entity.mysql.Employee;
import com.example.demo.repo.h2.UserTestRepository;
import com.example.demo.repo.mysql.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin() // open for all ports
@RequestMapping("crud")
@RestController
@EnableTransactionManagement
class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    UserTestRepository userRepository;

    /**
     * Get all the employees
     *
     * @return ResponseEntity
     */
    @GetMapping("/employees")
    @Transactional("h2TransactionManager")
    public ResponseEntity<List<Employee>> getEmployees() {

        Usertest user = new Usertest();
        user.setName("John");
        user.setEmail("john@test.com");
        user.setAge(20);


        try {
            user = userRepository.save(user);
            return new ResponseEntity(user,HttpStatus.OK);
            //return new ResponseEntity<>(employeeRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get the employee by id
     *
     * @param id
     * @return ResponseEntity
     */
    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id) {
        try {
            //check if employee exist in database
            Employee empObj = getEmpRec(id);

            if (empObj != null) {
                return new ResponseEntity<>(empObj, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Create new employee
     *
     * @param employee
     * @return ResponseEntity
     */
    @PostMapping("/employee")
    public ResponseEntity<Employee> newEmployee(@RequestBody Employee employee) {
        //create new employee
        Employee newEmployee = employeeRepository
                .save(employee);
        return new ResponseEntity<>(newEmployee, HttpStatus.OK);
    }

    /**
     * Update Employee record by using it's id
     *
     * @param id
     * @param employee
     * @return
     */
    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id, @RequestBody Employee employee) {

        //check if employee exist in database
        Employee empObj = getEmpRec(id);

        if (empObj != null) {
            empObj.setName(employee.getName());
            empObj.setRole(employee.getRole());
            return new ResponseEntity<>(employeeRepository.save(empObj), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Delete Employee by Id
     *
     * @param id
     * @return ResponseEntity
     */
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<HttpStatus> deleteEmployeeById(@PathVariable("id") long id) {
        try {
            //check if employee exist in database
            Employee emp = getEmpRec(id);

            if (emp != null) {
                employeeRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Delete all employees
     *
     * @return ResponseEntity
     */
    @DeleteMapping("/employees")
    public ResponseEntity<HttpStatus> deleteAllEmployees() {
        try {
            employeeRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Method to get the employee record by id
     *
     * @param id
     * @return Employee
     */
    private Employee getEmpRec(long id) {
        Optional<Employee> empObj = employeeRepository.findById(id);

        if (empObj.isPresent()) {
            return empObj.get();
        }
        return null;
    }

}