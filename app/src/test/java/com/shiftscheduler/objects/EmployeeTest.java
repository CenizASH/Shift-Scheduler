package com.shiftscheduler.objects;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

public class EmployeeTest {

    @Test
    public void testEmployee()
    {
        Employee employee;

        System.out.println("\nStarting testEmployee");

        employee = new Employee("1", "Alice", "alice@test.com", "developer", "1A3B5C");
        Employee employee2 = new Employee("1", "Alice", "alice@test.com", "developer", "1A3B5C");
        assertNotNull(employee);
        assertEquals("1", employee.getId());
        assertEquals("Alice", employee.getName());
        assertEquals("alice@test.com", employee.getEmail());
        assertEquals("developer", employee.getDepartment());
        assertEquals("1A3B5C", employee.getCode());
        assertEquals("Alice (User code: 1A3B5C)", employee.toString());
        assertEquals(employee, employee2);
        employee2.setName("Bob");
        employee2.setDepartment("manager");
        employee2.setEmail("bob@test.com");
        HashSet<Employee> employees = new HashSet<>();
        employees.add(employee2);
        assertFalse(employees.contains(employee));

        System.out.println("Finished testEmployee");
    }

}