package com.shiftscheduler.objects;

import static org.junit.Assert.*;

import org.junit.Test;

public class StatisticsTest {

    @Test
    public void testStatistics()
    {
        Statistics statistics;

        System.out.println("\nStarting testStatistics");

        Employee employee = new Employee("1", "Alice", "alice@test.com", "developer", "1A3B5C");
        statistics = new Statistics(employee, 40.0f, 480.0f);
        assertNotNull(statistics);
        assertEquals(480.0f, statistics.getYearHour(), 0.0f);
        assertEquals(40.0f, statistics.getMonthHour(), 0.0f);
        assertEquals(employee, statistics.getEmployee());

        System.out.println("Finished testStatistics");
    }

}