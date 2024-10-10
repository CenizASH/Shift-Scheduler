package com.shiftscheduler.persistence;

import com.shiftscheduler.objects.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoStub implements EmployeeDao {
    private List<Employee> employees = new ArrayList<>();


     public EmployeeDaoStub() {

         employees.add(new Employee("1", "Alice", "alice@test.com","developer", Employee.generateCode()));
         employees.add(new Employee("2", "Bob", "bob@test.com","developer", Employee.generateCode()));
         employees.add(new Employee("3", "Charlie", "charlie@test.com","developer", Employee.generateCode()));
         employees.add(new Employee("4", "David", "david@test.com","developer", Employee.generateCode()));
         employees.add(new Employee("5", "Eve", "eve@test.com","developer", Employee.generateCode()));
     }

    @Override
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }

    @Override
    public Employee getEmployeeById(String employeeId) {
        for (Employee employee : employees) {
            if (employee.getId().equals(employeeId)) {
                return employee;
            }
        }
        return null;
    }

    @Override
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    @Override
    public void updateEmployee(Employee employee) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId().equals(employee.getId())) {
                employees.set(i, employee);
                break;
            }
        }
    }

    @Override
    public void deleteEmployee(String employeeId) {
        employees.removeIf(e -> e.getId().equals(employeeId));
    }

    @Override
    public String getEmployeeIdByCode(String code) {
        for (Employee employee : employees) {
            if (employee.getCode().equals(code)) {
                return employee.getId();
            }
        }
        return null;
    }

    @Override
    public boolean existsEmail(String email) {
        for (Employee employee : employees) {
            if (employee.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}
