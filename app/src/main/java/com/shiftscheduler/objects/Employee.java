package com.shiftscheduler.objects;

import java.util.Objects;
import java.util.UUID;

public class Employee {
    private String id;
    private String name;
    private String email;
    private String department;
    private String code;

    public Employee(String id, String name, String email, String department, String code) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.code = code;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getCode() { return code; }

    @Override
    public String toString() {
        return name + " (User code: " + code + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(name, employee.name) && Objects.equals(email, employee.email) && Objects.equals(department, employee.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, department);
    }

    public static String generateCode() {
        // generate UUID with six characters
        return UUID.randomUUID().toString().substring(0, 6);
    }
}
