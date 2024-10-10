package com.shiftscheduler.business;

import com.shiftscheduler.objects.Account;
import com.shiftscheduler.persistence.AccountDao;

public class AccountManager {
    public static final String ACCOUNT_ALREADY_EXISTS = "Account already exists";
    public static final String EMPLOYEE_ID_ALREADY_BIND = "Employee code already bind";
    public static final String ACCOUNT_CREATED = "Account created successfully";
    private AccountDao accountDao;

    public AccountManager(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public String register(Account account) {
        if (accountDao.isAccountExist(account.getName())) {
            return ACCOUNT_ALREADY_EXISTS;
        }
        if (accountDao.isEmployeeIdBind(account.getId())) {
            return EMPLOYEE_ID_ALREADY_BIND;
        }
        accountDao.addAccount(account);
        return ACCOUNT_CREATED;
    }

    public Account login(String name, String password) {
        return accountDao.getAccount(name, password);
    }
}
