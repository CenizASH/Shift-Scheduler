package com.shiftscheduler.business;

import com.shiftscheduler.objects.Employee;
import com.shiftscheduler.persistence.EmployeeDao;

import java.util.List;

public class EmployeeManager {

    public static final String EMAIL_ALREADY_EXISTS = "Email already exists";
    public static final String EMPLOYEE_CREATED = "Employee create successfully";
    private EmployeeDao employeeDao;

    public EmployeeManager(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public String addEmployee(Employee employee) {
        boolean exists = employeeDao.existsEmail(employee.getEmail());
        if (exists) {
            return EMAIL_ALREADY_EXISTS;
        }
        employeeDao.addEmployee(employee);
        return EMPLOYEE_CREATED;
    }

    public void updateEmployee(Employee employee) {
        employeeDao.updateEmployee(employee);
    }

    public void deleteEmployee(String employeeId) {
        employeeDao.deleteEmployee(employeeId);
    }

    public Employee getEmployeeById(String employeeId) {
        return employeeDao.getEmployeeById(employeeId);
    }

    public String getEmployeeIdByCode(String code) {
        return employeeDao.getEmployeeIdByCode(code);
    }

    public List<Employee> getAllEmployees() {
        return employeeDao.getAllEmployees();
    }
}
