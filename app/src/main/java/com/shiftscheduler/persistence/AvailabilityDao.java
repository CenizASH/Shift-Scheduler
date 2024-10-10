package com.shiftscheduler.persistence;

import com.shiftscheduler.objects.Availability;

import java.util.List;

public interface AvailabilityDao {
    Availability getAvailabilityById(String availabilityId);
    List<Availability> getAllAvailabilities();
    List<Availability> getAvailabilityByEmployee(String employeeId);
    List<Availability> getAvailabilityByWeekDay(String weekDay);
    List<Availability> getAvailabilityByDay(String day);
    void addAvailability(Availability availability);
    void updateAvailability(Availability availability);
    void deleteAvailability(String availabilityId);
    void deleteAvailabilityByEmployee(String employeeId);
    void arrangeAvailabilitiesForEmployee(String employeeId);
}
