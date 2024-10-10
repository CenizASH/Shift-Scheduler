package com.shiftscheduler.application;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.shiftscheduler.business.AccountManager;
import com.shiftscheduler.business.AvailabilityManager;
import com.shiftscheduler.business.EmployeeManager;
import com.shiftscheduler.business.StatisticsManager;
import com.shiftscheduler.objects.Account;
import com.shiftscheduler.persistence.AccountDaoHSQLDB;
import com.shiftscheduler.persistence.AccountDaoStub;
import com.shiftscheduler.persistence.AvailabilityDaoHSQLDB;
import com.shiftscheduler.persistence.AvailabilityDaoStub;
import com.shiftscheduler.persistence.DatabaseHelper;
import com.shiftscheduler.persistence.EmployeeDAOHSQLDB;
import com.shiftscheduler.persistence.EmployeeDaoStub;

public class MyApplication extends Application {

    public static final String CHANNEL_ID = "employee_notifications";
    private AvailabilityManager availabilityManager;
    private EmployeeManager employeeManager;
    private AccountManager accountManager;
    private StatisticsManager statisticManager;
    private boolean useStub = false;
    private Account loginAccount;

    @Override
    public void onCreate() {
        super.onCreate();

        if (useStub) {
            availabilityManager = new AvailabilityManager(new AvailabilityDaoStub());
            employeeManager = new EmployeeManager(new EmployeeDaoStub());
            accountManager = new AccountManager(new AccountDaoStub());
            statisticManager = new StatisticsManager(new EmployeeDaoStub(), new AvailabilityDaoStub());
        } else {
            DatabaseHelper.setupDb(this);
            availabilityManager = new AvailabilityManager(new AvailabilityDaoHSQLDB());
            employeeManager = new EmployeeManager(new EmployeeDAOHSQLDB());
            accountManager = new AccountManager(new AccountDaoHSQLDB());
            statisticManager = new StatisticsManager(new EmployeeDAOHSQLDB(), new AvailabilityDaoHSQLDB());
        }
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Employee Notifications";
            String description = "Notifications for employee actions";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public AvailabilityManager getAvailabilityManager() {
        return availabilityManager;
    }

    public EmployeeManager getEmployeeManager() {
        return employeeManager;
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public StatisticsManager getStatisticManager() {
        return statisticManager;
    }

    public Account getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(Account account) {
        loginAccount = account;
    }
}
