package com.shiftscheduler.persistence;

import static org.junit.Assert.*;

import com.shiftscheduler.objects.Employee;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class EmployeeDaoStubTest {

    private EmployeeDao employeeDao;

    @Before
    public void setUp() {
        employeeDao = new EmployeeDaoStub();
    }

    @Test
    public void addEmployee() {
        Employee employee = new Employee("10", "Alex", "alex@testing.com", "Sales", "code");
        employeeDao.addEmployee(employee);
        assertEquals(employee, employeeDao.getEmployeeById("10"));
    }

    @Test
    public void updateEmployee() {
        Employee employee = new Employee("10", "Alex", "alex@testing.com", "Sales", "code");
        employeeDao.addEmployee(employee);
        employee.setName("Updated Alice");
        employeeDao.updateEmployee(employee);
        assertEquals("Updated Alice", employeeDao.getEmployeeById("10").getName());
    }

    @Test
    public void deleteEmployee() {
        Employee employee = new Employee("10", "Alex", "alex@testing.com", "Sales", "code");
        employeeDao.addEmployee(employee);
        employeeDao.deleteEmployee("10");
        assertEquals(null, employeeDao.getEmployeeById("10"));
    }

    @Test
    public void getEmployeeById() {
        Employee employee = new Employee("10", "Alex", "alex@testing.com", "Sales", "code");
        employeeDao.addEmployee(employee);
        Employee result = employeeDao.getEmployeeById("10");
        assertEquals(employee, result);
    }

    @Test
    public void getAllEmployees() {
        // remove all employees
        List<Employee> employees = employeeDao.getAllEmployees();
        for (Employee employee : employees) {
            employeeDao.deleteEmployee(employee.getId());
        }
        Employee employee1 = new Employee("10", "Alex", "alex@testing.com", "Sales", "codeAl");
        Employee employee2 = new Employee("20", "Boby", "boby@testing.com", "Sales", "codeBo");
        employeeDao.addEmployee(employee1);
        employeeDao.addEmployee(employee2);
        List<Employee> result = employeeDao.getAllEmployees();
        assertEquals(Arrays.asList(employee1, employee2), result);
    }

    @Test
    public void getEmployeeIdByCode() {
        Employee employee = new Employee("10", "Alex", "alex@testing.com", "Sales", "code");
        employeeDao.addEmployee(employee);
        String result = employeeDao.getEmployeeIdByCode("code");
        assertEquals("10", result);
    }


}