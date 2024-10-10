package com.shiftscheduler.persistence;

import com.shiftscheduler.objects.Availability;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AvailabilityDaoStub implements AvailabilityDao {
    private List<Availability> availabilities = new ArrayList<>();

    public AvailabilityDaoStub() {
        Date today = new Date();
        LocalDate localDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate monday = localDate.with(DayOfWeek.MONDAY);
        LocalDate tuesday = localDate.with(DayOfWeek.TUESDAY);
        LocalDate wednesday = localDate.with(DayOfWeek.WEDNESDAY);
        LocalDate thursday = localDate.with(DayOfWeek.THURSDAY);
        LocalDate friday = localDate.with(DayOfWeek.FRIDAY);
        LocalDate sunday = localDate.with(DayOfWeek.SUNDAY);

        availabilities.add(new Availability("1", "1", monday.toString(), "08:00", "16:00", new ArrayList<>(), new ArrayList<>(),"Monday"));
        availabilities.add(new Availability("2", "2", tuesday.toString(), "08:00", "16:00", new ArrayList<>(), new ArrayList<>(),"Tuesday"));
        availabilities.add(new Availability("3", "3", wednesday.toString(), "08:00", "16:00", new ArrayList<>(), new ArrayList<>(),"Wednesday"));
        availabilities.add(new Availability("4", "4", thursday.toString(), "08:00", "16:00", new ArrayList<>(), new ArrayList<>(),"Thursday"));
        availabilities.add(new Availability("5", "5", friday.toString(), "20:00", "22:00", new ArrayList<>(), new ArrayList<>(),"Friday"));
        availabilities.add(new Availability("6", "5", sunday.toString(), "16:00", "18:00", new ArrayList<>(), new ArrayList<>(),"Sunday"));
    }

    @Override
    public Availability getAvailabilityById(String availabilityId) {
        for (Availability availability : availabilities) {
            if (availability.getId().equals(availabilityId)) {
                return availability;
            }
        }
        return null;
    }

    @Override
    public List<Availability> getAllAvailabilities() {
        return new ArrayList<>(availabilities);
    }

    @Override
    public List<Availability> getAvailabilityByEmployee(String employeeId) {
        List<Availability> result = new ArrayList<>();
        for (Availability availability : availabilities) {
            if (availability.getEmployeeId().equals(employeeId)) {
                result.add(availability);
            }
        }
        return result;
    }

    @Override
    public List<Availability> getAvailabilityByWeekDay(String weekDay) {
        List<Availability> result = new ArrayList<>();
        for (Availability availability : availabilities) {
            if (availability.getWeekDay().equals(weekDay)) {
                result.add(availability);
            }
        }
        return result;
    }

    public List<Availability> getAvailabilityByDay(String day) {
        List<Availability> result = new ArrayList<>();
        for (Availability availability : availabilities) {
            if (availability.getDate().equals(day)) {
                result.add(availability);
            }
        }
        return result;
    }

    @Override
    public void addAvailability(Availability availability) {
        availabilities.add(availability);
    }

    @Override
    public void updateAvailability(Availability availability) {
        for (int i = 0; i < availabilities.size(); i++) {
            if (availabilities.get(i).getId().equals(availability.getId())) {
                availabilities.set(i, availability);
                break;
            }
        }
    }

    @Override
    public void deleteAvailability(String availabilityId) {
        availabilities.removeIf(a -> a.getId().equals(availabilityId));
    }

    @Override
    public void deleteAvailabilityByEmployee(String employeeId) {
        availabilities.removeIf(a -> a.getEmployeeId().equals(employeeId));
    }

    public void arrangeAvailabilitiesForEmployee(String employeeId) {
        for (int i = 0; i < availabilities.size(); i++) {
            if (availabilities.get(i).getEmployeeId().equals(employeeId)) {
                availabilities.get(i).setArranged(true);
            }
        }
    }
}
