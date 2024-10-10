package com.shiftscheduler;

import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

// Specify the test classes to include in the suite
@Suite.SuiteClasses({
        AvailabilityActivityTest.class,
        EmployeeActivityTest.class,
        GlanceActivityTest.class,
        LoginTest.class,
        RegisterTest.class,
        StatisticsActivityTest.class
})
public class AllTests extends TestSuite {

}

