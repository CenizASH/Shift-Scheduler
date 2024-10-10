package com.shiftscheduler.business;

import com.shiftscheduler.objects.Availability;
import com.shiftscheduler.persistence.AvailabilityDao;

import java.util.List;

public class AvailabilityManager {
    private AvailabilityDao availabilityDao;

    public AvailabilityManager(AvailabilityDao availabilityDao) {
        this.availabilityDao = availabilityDao;
    }

    public void createAvailability(Availability availability) {
        availabilityDao.addAvailability(availability);
    }

    public Availability getAvailabilityById(String availabilityId) {
        return availabilityDao.getAvailabilityById(availabilityId);
    }

    public void modifyAvailability(Availability availability) {
        availabilityDao.updateAvailability(availability);
    }

    public void removeAvailability(String availabilityId) {
        availabilityDao.deleteAvailability(availabilityId);
    }

    public void removeAvailabilityByEmployee(String employeeId) {
        availabilityDao.deleteAvailabilityByEmployee(employeeId);
    }

    public List<Availability> getAllAvailabilities() {
        return availabilityDao.getAllAvailabilities();
    }

    public List<Availability> getAvailabilitiesForEmployee(String employeeId) {
        return availabilityDao.getAvailabilityByEmployee(employeeId);
    }

    public List<Availability> getAvailabilitiesForWeekDay(String weekDay) {
        return availabilityDao.getAvailabilityByWeekDay(weekDay);
    }

    public List<Availability> getAvailabilitiesByDay(String day) {
       return availabilityDao.getAvailabilityByDay(day);
    }

    public void arrangeAvailabilitiesForEmployee(String employeeId) {
        availabilityDao.arrangeAvailabilitiesForEmployee(employeeId);
    }
}
