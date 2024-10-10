package com.shiftscheduler.persistence;

import static org.junit.Assert.*;

import com.shiftscheduler.business.AvailabilityManager;
import com.shiftscheduler.objects.Availability;

import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AvailabilityDaoStubTest {

    private AvailabilityDao availabilityDaoStub;

    @Before
    public void setUp() {
        availabilityDaoStub = new AvailabilityDaoStub();
    }

    @Test
    public void createAvailability() {
        Availability availability = new Availability("10", "1", "2024-6-2", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(), "Sunday");
        availabilityDaoStub.addAvailability(availability);
        assertEquals(availability, availabilityDaoStub.getAvailabilityById("10"));
    }

    @Test
    public void modifyAvailability() {
        Availability availability = new Availability("11", "1", "2024-6-2", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(), "Sunday");
        availabilityDaoStub.addAvailability(availability);
        availability.setWeekDay("Tuesday");
        availabilityDaoStub.updateAvailability(availability);
        assertEquals("Tuesday", availabilityDaoStub.getAvailabilityById("11").getWeekDay());
    }

    @Test
    public void removeAvailability() {
        Availability availability = new Availability("12", "1", "2024-6-2", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(), "Sunday");
        availabilityDaoStub.addAvailability(availability);
        availabilityDaoStub.deleteAvailability("12");
        assertEquals(null, availabilityDaoStub.getAvailabilityById("12"));
    }

    @Test
    public void removeAvailabilityByEmployee() {
        Availability availability1 = new Availability("13", "1", "2024-6-2", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(), "Sunday");
        Availability availability2 = new Availability("14", "1", "2024-6-2", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(), "Sunday");
        availabilityDaoStub.addAvailability(availability1);
        availabilityDaoStub.addAvailability(availability2);
        availabilityDaoStub.deleteAvailabilityByEmployee("1");
        assertEquals(null, availabilityDaoStub.getAvailabilityById("13"));
        assertEquals(null, availabilityDaoStub.getAvailabilityById("14"));
        assertEquals(0, availabilityDaoStub.getAvailabilityByEmployee("1").size());
    }

    @Test
    public void getAllAvailabilities() {
        // remove all availabilities
        List<Availability> availabilities = availabilityDaoStub.getAllAvailabilities();
        for (Availability availability : availabilities) {
            availabilityDaoStub.deleteAvailability(availability.getId());
        }
        Availability availability1 = new Availability("15", "1", "2024-6-2", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(), "Sunday");
        Availability availability2 = new Availability("16", "1", "2024-6-2", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(), "Sunday");
        availabilityDaoStub.addAvailability(availability1);
        availabilityDaoStub.addAvailability(availability2);
        List<Availability> result = availabilityDaoStub.getAllAvailabilities();
        assertEquals(Arrays.asList(availability1, availability2), result);
    }

    @Test
    public void getAvailabilitiesForEmployee() {
        // remove all availabilities
        List<Availability> availabilities = availabilityDaoStub.getAllAvailabilities();
        for (Availability availability : availabilities) {
            availabilityDaoStub.deleteAvailability(availability.getId());
        }
        Availability availability1 = new Availability("17", "1", "2024-6-2", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(), "Sunday");
        Availability availability2 = new Availability("18", "1", "2024-6-2", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(), "Sunday");
        availabilityDaoStub.addAvailability(availability1);
        availabilityDaoStub.addAvailability(availability2);
        List<Availability> result = availabilityDaoStub.getAvailabilityByEmployee("1");
        assertEquals(Arrays.asList(availability1, availability2), result);
    }

    @Test
    public void getAvailabilitiesForWeekDay() {
        // remove all availabilities
        List<Availability> availabilities = availabilityDaoStub.getAllAvailabilities();
        for (Availability availability : availabilities) {
            availabilityDaoStub.deleteAvailability(availability.getId());
        }
        Availability availability1 = new Availability("19", "1", "2024-6-2", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(), "Sunday");
        Availability availability2 = new Availability("20", "1", "2024-6-2", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(), "Sunday");
        availabilityDaoStub.addAvailability(availability1);
        availabilityDaoStub.addAvailability(availability2);
        List<Availability> result = availabilityDaoStub.getAvailabilityByWeekDay("Sunday");
        assertEquals(Arrays.asList(availability1, availability2), result);
    }

    @Test
    public void getAvailabilitiesByDay() {
        Date today = new Date();
        LocalDate localDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate monday = localDate.with(DayOfWeek.MONDAY);
        assertNotNull(availabilityDaoStub.getAvailabilityByDay(monday.toString()));
    }

    @Test
    public void arrangeAvailabilitiesForEmployee() {
        // remove all availabilities
        List<Availability> availabilities = availabilityDaoStub.getAllAvailabilities();
        for (Availability availability : availabilities) {
            availabilityDaoStub.deleteAvailability(availability.getId());
        }
        Availability availability1 = new Availability("21", "1", "2024-6-2", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(), "Sunday");
        Availability availability2 = new Availability("22", "1", "2024-6-2", "08:00", "16:00", new ArrayList<>(), new ArrayList<>(), "Sunday");
        availabilityDaoStub.addAvailability(availability1);
        availabilityDaoStub.addAvailability(availability2);
        availabilityDaoStub.arrangeAvailabilitiesForEmployee("1");
        assertTrue(availabilityDaoStub.getAvailabilityById("21").isArranged());
        assertTrue(availabilityDaoStub.getAvailabilityById("22").isArranged());
    }

}