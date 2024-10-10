package com.shiftscheduler.business;


import com.shiftscheduler.objects.Availability;
import com.shiftscheduler.persistence.AvailabilityDao;
import com.shiftscheduler.persistence.AvailabilityDaoStub;

import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class AvailabilityManagerTest {

    private AvailabilityManager availabilityManager;

    @Before
    public void setUp() {
        AvailabilityDao availabilityDaoStub = new AvailabilityDaoStub();
        availabilityManager = new AvailabilityManager(availabilityDaoStub);
    }

    @Test
    public void createAvailability() {
        Availability availability = new Availability("10", "1", "2024-6-2", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(), "Sunday");
        availabilityManager.createAvailability(availability);
        assertEquals(availability, availabilityManager.getAvailabilityById("10"));
    }

    @Test
    public void modifyAvailability() {
        Availability availability = new Availability("11", "1", "2024-6-2", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(), "Sunday");
        availabilityManager.createAvailability(availability);
        availability.setWeekDay("Tuesday");
        availabilityManager.modifyAvailability(availability);
        assertEquals("Tuesday", availabilityManager.getAvailabilityById("11").getWeekDay());
    }

    @Test
    public void removeAvailability() {
        Availability availability = new Availability("12", "1", "2024-6-2", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(), "Sunday");
        availabilityManager.createAvailability(availability);
        availabilityManager.removeAvailability("12");
        assertEquals(null, availabilityManager.getAvailabilityById("12"));
    }

    @Test
    public void removeAvailabilityByEmployee() {
        Availability availability1 = new Availability("13", "1", "2024-6-2", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(), "Sunday");
        Availability availability2 = new Availability("14", "1", "2024-6-2", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(), "Sunday");
        availabilityManager.createAvailability(availability1);
        availabilityManager.createAvailability(availability2);
        availabilityManager.removeAvailabilityByEmployee("1");
        assertEquals(null, availabilityManager.getAvailabilityById("13"));
        assertEquals(null, availabilityManager.getAvailabilityById("14"));
        assertEquals(0, availabilityManager.getAvailabilitiesForEmployee("1").size());
    }

    @Test
    public void getAllAvailabilities() {
        // remove all availabilities
        List<Availability> availabilities = availabilityManager.getAllAvailabilities();
        for (Availability availability : availabilities) {
            availabilityManager.removeAvailability(availability.getId());
        }
        Availability availability1 = new Availability("15", "1", "2024-6-2", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(), "Sunday");
        Availability availability2 = new Availability("16", "1", "2024-6-2", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(), "Sunday");
        availabilityManager.createAvailability(availability1);
        availabilityManager.createAvailability(availability2);
        List<Availability> result = availabilityManager.getAllAvailabilities();
        assertEquals(Arrays.asList(availability1, availability2), result);
    }

    @Test
    public void getAvailabilitiesForEmployee() {
        // remove all availabilities
        List<Availability> availabilities = availabilityManager.getAllAvailabilities();
        for (Availability availability : availabilities) {
            availabilityManager.removeAvailability(availability.getId());
        }
        Availability availability1 = new Availability("17", "1", "2024-6-2", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(), "Sunday");
        Availability availability2 = new Availability("18", "1", "2024-6-2", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(), "Sunday");
        availabilityManager.createAvailability(availability1);
        availabilityManager.createAvailability(availability2);
        List<Availability> result = availabilityManager.getAvailabilitiesForEmployee("1");
        assertEquals(Arrays.asList(availability1, availability2), result);
    }

    @Test
    public void getAvailabilitiesForWeekDay() {
        // remove all availabilities
        List<Availability> availabilities = availabilityManager.getAllAvailabilities();
        for (Availability availability : availabilities) {
            availabilityManager.removeAvailability(availability.getId());
        }
        Availability availability1 = new Availability("19", "1", "2024-6-2", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(), "Sunday");
        Availability availability2 = new Availability("20", "1", "2024-6-2", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(), "Sunday");
        availabilityManager.createAvailability(availability1);
        availabilityManager.createAvailability(availability2);
        List<Availability> result = availabilityManager.getAvailabilitiesForWeekDay("Sunday");
        assertEquals(Arrays.asList(availability1, availability2), result);
    }

    @Test
    public void getAvailabilitiesByDay() {
        Date today = new Date();
        LocalDate localDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate monday = localDate.with(DayOfWeek.MONDAY);
        assertNotNull(availabilityManager.getAvailabilitiesByDay(monday.toString()));
    }

    @Test
    public void arrangeAvailabilitiesForEmployee() {
        // remove all availabilities
        List<Availability> availabilities = availabilityManager.getAllAvailabilities();
        for (Availability availability : availabilities) {
            availabilityManager.removeAvailability(availability.getId());
        }
        Availability availability1 = new Availability("21", "1", "2024-6-2", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(), "Sunday");
        Availability availability2 = new Availability("22", "1", "2024-6-2", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(), "Sunday");
        availabilityManager.createAvailability(availability1);
        availabilityManager.createAvailability(availability2);
        availabilityManager.arrangeAvailabilitiesForEmployee("1");
        assertTrue(availabilityManager.getAvailabilityById("21").isArranged());
        assertTrue(availabilityManager.getAvailabilityById("22").isArranged());
    }
}
