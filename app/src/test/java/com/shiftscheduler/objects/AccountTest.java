package com.shiftscheduler.objects;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.HashSet;

public class AccountTest {

    @Test
    public void testAccount()
    {
        Account account;

        System.out.println("\nStarting testAccount");

        account = new Account("1", "manager", "manager", "test@test.com", "manager");
        Account account2 = new Account("1", "manager", "manager", "test@test.com", "manager");
//        Account account2 = new Account("2", "employee", "employee", "test@test.com", "employee");
        assertNotNull(account);
        assertEquals("1", account.getId());
        assertEquals("manager", account.getName());
        assertEquals("manager", account.getPassword());
        assertEquals("test@test.com", account.getEmail());
        assertEquals("manager", account.getType());
        assertEquals("Account{id='1', name='manager', password='manager', email='test@test.com', type='manager'}", account.toString());

        assertEquals(account, account2);
        account2.setName("employee");
        account2.setPassword("employee");
        account2.setEmail("test@test.com");
        account2.setType("employee");
        HashSet<Account> accounts = new HashSet<>();
        accounts.add(account2);
        assertFalse(accounts.contains(account));

        System.out.println("Finished testAccount");
    }

}