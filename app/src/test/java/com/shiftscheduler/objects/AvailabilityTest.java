package com.shiftscheduler.objects;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class AvailabilityTest {

    @Test
    public void testAvailability()
    {
        Availability availability;

        System.out.println("\nStarting testAvailability");

        Date today = new Date();
        LocalDate localDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate monday = localDate.with(DayOfWeek.MONDAY);
        LocalDate tuesday = localDate.with(DayOfWeek.TUESDAY);
        availability = new Availability("1", "1", monday.toString(), "08:00", "16:00", new ArrayList<>(), new ArrayList<>(),"Monday");
//        Availability availability2 = new Availability("2", "2", tuesday.toString(), "08:00", "16:00", new ArrayList<>(), new ArrayList<>(),"Tuesday"));
        Availability availability2 = new Availability("1", "1", monday.toString(), "08:00", "16:00", new ArrayList<>(), new ArrayList<>(),"Monday");

        assertNotNull(availability);
        assertEquals("1", availability.getId());
        assertEquals(monday.toString(), availability.getDate());
        assertEquals("08:00", availability.getStartTime());
        assertEquals("16:00", availability.getEndTime());
        assertEquals("Monday", availability.getWeekDay());
        assertEquals(0, availability.getPreferences().size());
        assertEquals(0, availability.getAvoids().size());
        assertEquals("Availability{id='1', employeeId='1', date='" + monday.toString() + "', weekDay='Monday', startTime='08:00', endTime='16:00', preferences=[], avoids=[], arranged=false}", availability.toString());
        assertEquals(availability, availability2);

        availability2.setId("2");
        availability2.setEmployeeId("2");
        availability2.setDate(tuesday.toString());
        availability2.setWeekDay("Tuesday");
        availability2.setStartTime("08:00");
        availability2.setEndTime("16:00");
        availability2.setPreferences(new ArrayList<>());
        availability2.setAvoids(new ArrayList<>());
        HashSet<Availability> availabilities = new HashSet<>();
        availabilities.add(availability2);
        assertFalse(availabilities.contains(availability));

        System.out.println("Finished testAvailability");
    }

}