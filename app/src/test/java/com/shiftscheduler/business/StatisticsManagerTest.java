package com.shiftscheduler.business;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shiftscheduler.objects.Availability;
import com.shiftscheduler.objects.Employee;
import com.shiftscheduler.objects.Statistics;
import com.shiftscheduler.persistence.AvailabilityDao;
import com.shiftscheduler.persistence.EmployeeDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StatisticsManagerTest {

    private EmployeeDao employeeDao;
    private AvailabilityDao availabilityDao;
    private StatisticsManager statisticManager;


    @Before
    public void setUp() throws Exception {
        employeeDao = mock(EmployeeDao.class);
        availabilityDao = mock(AvailabilityDao.class);
        statisticManager = new StatisticsManager(employeeDao, availabilityDao);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void statisticsAll() {
        final List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("1", "Alice", "alice@test.com","developer", Employee.generateCode()));
        when(employeeDao.getAllEmployees()).thenReturn(employees);
        final List<Availability> availabilities = new ArrayList<>();
        Availability availability = new Availability("1", "1", "2024-08-12", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(),"Monday");
        availability.setArranged(true);
        availabilities.add(availability);
        when(availabilityDao.getAvailabilityByEmployee("1")).thenReturn(availabilities);

        final List<Statistics> statistics = statisticManager.statisticsAll();
        assertEquals(1, statistics.size());
        assertEquals(8, statistics.get(0).getMonthHour(), 0.1);
        assertEquals(8, statistics.get(0).getYearHour(), 0.1);

        verify(availabilityDao).getAvailabilityByEmployee("1");
        verify(employeeDao).getAllEmployees();
    }

    @Test
    public void statisticsByEmployee() {

    }
}