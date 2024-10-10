package com.shiftscheduler.persistence;

import com.shiftscheduler.objects.Account;

public interface AccountDao {
    Account getAccount(String name, String password);
    void addAccount(Account account);
    boolean isAccountExist(String name);
    boolean isEmployeeIdBind(String employeeId);
}
