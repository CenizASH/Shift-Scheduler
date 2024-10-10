package com.shiftscheduler.business;

import com.shiftscheduler.objects.Employee;
import com.shiftscheduler.persistence.EmployeeDao;
import com.shiftscheduler.persistence.EmployeeDaoStub;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EmployeeManagerTest {

    private EmployeeDao employeeDao;

    private EmployeeManager employeeManager;

    @Before
    public void setUp() {
        employeeDao = new EmployeeDaoStub();
        employeeManager = new EmployeeManager(employeeDao);
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
