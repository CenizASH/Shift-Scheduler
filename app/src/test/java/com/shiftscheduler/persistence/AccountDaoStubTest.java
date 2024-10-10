package com.shiftscheduler.persistence;

import static org.junit.Assert.*;

import com.shiftscheduler.business.AccountManager;
import com.shiftscheduler.objects.Account;

import org.junit.Before;
import org.junit.Test;

public class AccountDaoStubTest {

    private AccountDao accountDaoStub;

    @Before
    public void setUp() {
        accountDaoStub = new AccountDaoStub();
    }

    @Test
    public void addSuccess() {
        Account account = new Account("10", "John", "password", "john@test.com",
                "employee");
        accountDaoStub.addAccount(account);
        assertEquals(account, accountDaoStub.getAccount("John", "password"));
    }

    @Test
    public void registerFailSameName() {
        Account account = new Account("10", "John", "password", "john@test.com",
                "employee");
        accountDaoStub.addAccount(account);
        assertTrue(accountDaoStub.isAccountExist("John"));
    }

    public void registerFailSameId() {
        Account account = new Account("10", "John", "password", "john@test.com",
                "employee");
        accountDaoStub.addAccount(account);
        assertTrue(accountDaoStub.isEmployeeIdBind("10"));
    }

}