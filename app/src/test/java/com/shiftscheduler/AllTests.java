package com.shiftscheduler;

import com.shiftscheduler.business.AccountManagerTest;
import com.shiftscheduler.business.AvailabilityManagerTest;
import com.shiftscheduler.business.EmployeeManagerTest;
import com.shiftscheduler.business.StatisticsManagerTest;
import com.shiftscheduler.objects.AccountTest;
import com.shiftscheduler.objects.AvailabilityTest;
import com.shiftscheduler.objects.EmployeeTest;
import com.shiftscheduler.objects.StatisticsTest;
import com.shiftscheduler.persistence.AccountDaoStubTest;
import com.shiftscheduler.persistence.AvailabilityDaoStubTest;
import com.shiftscheduler.persistence.EmployeeDaoStubTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        StatisticsTest.class,
        EmployeeTest.class,
        AvailabilityTest.class,
        AccountTest.class,
        AccountDaoStubTest.class,
        AvailabilityDaoStubTest.class,
        EmployeeDaoStubTest.class,
        StatisticsManagerTest.class,
        EmployeeManagerTest.class,
        AvailabilityManagerTest.class,
        AccountManagerTest.class
})
public class AllTests {

}
