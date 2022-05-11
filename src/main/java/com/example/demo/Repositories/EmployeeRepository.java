package com.example.demo.Repositories;

import com.example.demo.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByNameContainingIgnoreCase(String employeeName);

    Employee findByName(String name);
    
    void deleteAllByName(String name);
}
