package com.shiftscheduler.business;

import com.shiftscheduler.objects.Availability;
import com.shiftscheduler.objects.Employee;
import com.shiftscheduler.objects.Statistics;
import com.shiftscheduler.persistence.AvailabilityDao;
import com.shiftscheduler.persistence.EmployeeDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StatisticsManager {
    private EmployeeDao employeeDao;
    private AvailabilityDao availabilityDao;

    public StatisticsManager(EmployeeDao employeeDao, AvailabilityDao availabilityDao) {
        this.employeeDao = employeeDao;
        this.availabilityDao = availabilityDao;
    }

    public List<Statistics> statisticsAll() {
        List<Employee> employees = employeeDao.getAllEmployees();
        List<Statistics> statistics = new ArrayList<>();
        for (Employee employee : employees) {
            statistics.add(statisticsByEmployee(employee));
        }
        return statistics;
    }

    public Statistics statisticsByEmployee(Employee employee) {
        List<Availability> availabilities = availabilityDao.getAvailabilityByEmployee(employee.getId());
        float monthHour = 0;
        float yearHour = 0;
        for (Availability availability : availabilities) {
            float workHour = calculateHour(availability.getStartTime(), availability.getEndTime());
            if (availability.isArranged()) {
                if (isSameYear(availability.getDate())) {
                    yearHour += workHour;
                    if (isSameMonth(availability.getDate())) {
                        monthHour += workHour;
                    }
                }
            }
        }
        return new Statistics(employee, monthHour, yearHour);
    }

    private float calculateHour(String startTime, String endTime) {
        int startHour = Integer.parseInt(startTime.substring(0, 2));
        int startMinute = Integer.parseInt(startTime.substring(3, 5));
        startMinute += startHour * 60;
        int endHour = Integer.parseInt(endTime.substring(0, 2));
        int endMinute = Integer.parseInt(endTime.substring(3, 5));
        endMinute += endHour * 60;
        return (float) ((endMinute - startMinute) / 60.0);
    }

    private boolean isSameMonth(String date) {
        LocalDate localDate = LocalDate.now();
        return Integer.parseInt(date.substring(5, 7))==localDate.getMonthValue();
    }

    private boolean isSameYear(String date) {
        LocalDate localDate = LocalDate.now();
        return Integer.parseInt(date.substring(0, 4))==localDate.getYear();
    }
}
