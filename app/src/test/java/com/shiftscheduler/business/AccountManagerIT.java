package com.shiftscheduler.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.shiftscheduler.objects.Account;
import com.shiftscheduler.persistence.AccountDao;
import com.shiftscheduler.persistence.AccountDaoHSQLDB;
import com.shiftscheduler.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class AccountManagerIT {
    private AccountManager accountManager;
    private File tempDB;

    @Before
    public void setUp() throws Exception {
        this.tempDB = TestUtils.copyDB();
        AccountDao mockDao = new AccountDaoHSQLDB();
        accountManager = new AccountManager(mockDao);
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
    }

    @Test
    public void registerSuccess() {
        Account account = new Account("10", "John", "password", "john@test.com",
                "employee");
        accountManager.register(account);
        assertEquals(account, accountManager.login("John", "password"));
    }

    @Test
    public void registerFailSameName() {
        Account account = new Account("10", "John", "password", "john@test.com",
                "employee");
        accountManager.register(account);
        assertEquals(AccountManager.ACCOUNT_ALREADY_EXISTS, accountManager.register(account));
    }

    public void registerFailSameId() {
        Account account = new Account("10", "John", "password", "john@test.com",
                "employee");
        accountManager.register(account);
        Account account2 = new Account("10", "John2", "password2", "john@test.com",
                "employee");
        assertEquals(AccountManager.EMPLOYEE_ID_ALREADY_BIND, accountManager.register(account2));
    }

    @Test
    public void loginSuccess() {
        Account account = new Account("20", "John", "password", "john@test.com",
                "employee");
        accountManager.register(account);
        assertEquals(account, accountManager.login("John", "password"));
    }

    @Test
    public void loginFail() {
        Account account = new Account("20", "John", "password", "john@test.com",
                "employee");
        accountManager.register(account);
        assertNull(accountManager.login("John1", "password1"));
    }
}
