package com.shiftscheduler.persistence;

import com.shiftscheduler.objects.Account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDaoHSQLDB implements AccountDao {

    @Override
    public Account getAccount(String name, String password) {
        String sql = "SELECT * FROM accounts WHERE name = ? AND password = ?";
        try (PreparedStatement pst = DatabaseHelper.getConnection().prepareStatement(sql)) {
            pst.setString(1, name);
            pst.setString(2, password);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Account(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("password"),
                            rs.getString("email"),
                            rs.getString("type")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addAccount(Account account) {
        String sql = "INSERT INTO accounts (id, name, password, email, type) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = DatabaseHelper.getConnection().prepareStatement(sql)) {
            pst.setString(1, account.getId());
            pst.setString(2, account.getName());
            pst.setString(3, account.getPassword());
            pst.setString(4, account.getEmail());
            pst.setString(5, account.getType());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isAccountExist(String name) {
        String sql = "SELECT COUNT(*) FROM accounts WHERE name = ?";
        try (PreparedStatement pst = DatabaseHelper.getConnection().prepareStatement(sql)) {
            pst.setString(1, name);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isEmployeeIdBind(String employeeId) {
        String sql = "SELECT COUNT(*) FROM accounts WHERE id = ?";
        try (PreparedStatement pst = DatabaseHelper.getConnection().prepareStatement(sql)) {
            pst.setString(1, employeeId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
