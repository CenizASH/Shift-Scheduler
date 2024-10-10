package com.shiftscheduler;

import com.shiftscheduler.business.AccountManagerIT;
import com.shiftscheduler.business.AvailabilityManagerIT;
import com.shiftscheduler.business.EmployeeManagerIT;
import com.shiftscheduler.business.StatisticsManagerIT;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccountManagerIT.class,
        AvailabilityManagerIT.class,
        EmployeeManagerIT.class,
        StatisticsManagerIT.class
})
public class IntegrationTests {
}
