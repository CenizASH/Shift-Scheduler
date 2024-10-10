package com.shiftscheduler.business;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shiftscheduler.objects.Availability;
import com.shiftscheduler.objects.Employee;
import com.shiftscheduler.objects.Statistics;
import com.shiftscheduler.persistence.AvailabilityDao;
import com.shiftscheduler.persistence.AvailabilityDaoHSQLDB;
import com.shiftscheduler.persistence.AvailabilityDaoStub;
import com.shiftscheduler.persistence.DatabaseHelper;
import com.shiftscheduler.persistence.EmployeeDAOHSQLDB;
import com.shiftscheduler.persistence.EmployeeDao;
import com.shiftscheduler.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StatisticsManagerIT {

    private EmployeeDao employeeDao;
    private AvailabilityDao availabilityDao;
    private StatisticsManager statisticManager;
    private File tempDB;

    @Before
    public void setUp() throws Exception {
        this.tempDB = TestUtils.copyDB();
        employeeDao = new EmployeeDAOHSQLDB();
        availabilityDao = new AvailabilityDaoHSQLDB();
        statisticManager = new StatisticsManager(employeeDao, availabilityDao);
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
    }

    @Test
    public void testStatisticsAll() {
        final List<Statistics> statistics = statisticManager.statisticsAll();
        assertEquals(employeeDao.getAllEmployees().size(), statistics.size());
    }

    @Test
    public void testStatisticsByEmployee() {
        Availability availability = new Availability("1", "1", "2024-08-12", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(),"Monday");
        availability.setArranged(true);
        availabilityDao.addAvailability(availability);
        final Statistics statistics = statisticManager.statisticsByEmployee(employeeDao.getEmployeeById("1"));
        assertEquals(8, statistics.getMonthHour(), 0.1);
        assertEquals(8, statistics.getYearHour(), 0.1);
    }
}