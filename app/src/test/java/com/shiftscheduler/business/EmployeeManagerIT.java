package com.shiftscheduler.business;

import static org.junit.Assert.assertEquals;

import com.shiftscheduler.objects.Employee;
import com.shiftscheduler.persistence.EmployeeDAOHSQLDB;
import com.shiftscheduler.persistence.EmployeeDao;
import com.shiftscheduler.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class EmployeeManagerIT {
    private EmployeeDao mockDao;
    private File tempDB;

    private EmployeeManager employeeManager;

    @Before
    public void setUp() throws Exception {
        this.tempDB = TestUtils.copyDB();
        mockDao = new EmployeeDAOHSQLDB();
        employeeManager = new EmployeeManager(mockDao);
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
    }

    @Test
    public void addEmployee() {
        Employee employee = new Employee("10", "Alex", "alex@testing.com", "Sales", "code");
        employeeManager.addEmployee(employee);
        assertEquals(employee, employeeManager.getEmployeeById("10"));
    }

    @Test
    public void updateEmployee() {
        Employee employee = new Employee("10", "Alex", "alex@testing.com", "Sales", "code");
        employeeManager.addEmployee(employee);
        employee.setName("Updated Alice");
        employeeManager.updateEmployee(employee);
        assertEquals("Updated Alice", employeeManager.getEmployeeById("10").getName());
    }

    @Test
    public void deleteEmployee() {
        Employee employee = new Employee("10", "Alex", "alex@testing.com", "Sales", "code");
        employeeManager.addEmployee(employee);
        employeeManager.deleteEmployee("10");
        assertEquals(null, employeeManager.getEmployeeById("10"));
    }


    @Test
    public void getEmployeeById() {
        Employee employee = new Employee("10", "Alex", "alex@testing.com", "Sales", "code");
        employeeManager.addEmployee(employee);
        Employee result = employeeManager.getEmployeeById("10");
        assertEquals(employee, result);
    }

    @Test
    public void getAllEmployees() {
        // remove all employees
        List<Employee> employees = employeeManager.getAllEmployees();
        for (Employee employee : employees) {
            employeeManager.deleteEmployee(employee.getId());
        }
        Employee employee1 = new Employee("10", "Alex", "alex@testing.com", "Sales", "codeAl");
        Employee employee2 = new Employee("20", "Boby", "boby@testing.com", "Sales", "codeBo");
        employeeManager.addEmployee(employee1);
        employeeManager.addEmployee(employee2);
        List<Employee> result = employeeManager.getAllEmployees();
        assertEquals(Arrays.asList(employee1, employee2), result);
    }

    @Test
    public void getEmployeeIdByCode() {
        Employee employee = new Employee("10", "Alex", "alex@testing.com", "Sales", "code");
        employeeManager.addEmployee(employee);
        String result = employeeManager.getEmployeeIdByCode("code");
        assertEquals("10", result);
    }
}
