package com.shiftscheduler.persistence;

import com.shiftscheduler.objects.Employee;

import java.util.List;

public interface EmployeeDao {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(String employeeId);
    void addEmployee(Employee employee);
    void updateEmployee(Employee employee);
    void deleteEmployee(String employeeId);
    String getEmployeeIdByCode(String code);
    boolean existsEmail(String email);
}
