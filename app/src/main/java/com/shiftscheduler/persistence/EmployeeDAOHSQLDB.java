package com.shiftscheduler.persistence;

import com.shiftscheduler.objects.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOHSQLDB implements EmployeeDao {

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                employees.add(new Employee(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("department"),
                        rs.getString("code")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public Employee getEmployeeById(String employeeId) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, employeeId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Employee(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("department"),
                            rs.getString("code")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addEmployee(Employee employee) {
        String sql = "INSERT INTO employees (id, name, email, department,code) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, employee.getId());
            pst.setString(2, employee.getName());
            pst.setString(3, employee.getEmail());
            pst.setString(4, employee.getDepartment());
            pst.setString(5, employee.getCode());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEmployee(Employee employee) {
        String sql = "UPDATE employees SET name = ?, email = ?, department = ? WHERE id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, employee.getName());
            pst.setString(2, employee.getEmail());
            pst.setString(3, employee.getDepartment());
            pst.setString(4, employee.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEmployee(String employeeId) {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, employeeId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getEmployeeIdByCode(String code) {
        String sql = "SELECT id FROM employees WHERE code = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, code);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean existsEmail(String email) {
        String sql = "SELECT COUNT(*) FROM employees WHERE email = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
