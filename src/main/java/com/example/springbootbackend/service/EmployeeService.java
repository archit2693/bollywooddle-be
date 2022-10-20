package com.example.springbootbackend.service;

import com.example.springbootbackend.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long employeeId);
    Employee updateEmployee(Employee employeeDetails, Long employeeId);
    void deleteEmployee(Long employeeId);
}
