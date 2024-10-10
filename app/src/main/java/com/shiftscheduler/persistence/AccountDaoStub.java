package com.shiftscheduler.persistence;

import com.shiftscheduler.objects.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountDaoStub implements AccountDao {

    private List<Account> accounts = new ArrayList<>();

    public AccountDaoStub() {
        Account account1 = new Account("1", "manager", "manager", "test@test.com", "manager");
        accounts.add(account1);
        Account account2 = new Account("2", "employee", "employee", "test@test.com", "employee");
        accounts.add(account2);
    }

    @Override
    public Account getAccount(String name, String password) {
        // find the account with the given name and password
        for (Account account : accounts) {
            if (account.getName().equals(name) && account.getPassword().equals(password)) {
                return account;
            }
        }
        return null;
    }

    @Override
    public void addAccount(Account account) {
        accounts.add(account);
    }

    @Override
    public boolean isAccountExist(String name) {
        for (Account account : accounts) {
            if (account.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEmployeeIdBind(String employeeId) {
        for (Account account : accounts) {
            if (account.getId().equals(employeeId)) {
                return true;
            }
        }
        return false;
    }
}
