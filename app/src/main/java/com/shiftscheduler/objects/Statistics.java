package com.shiftscheduler.objects;

public class Statistics {
    private Employee employee;
    private float monthHour;
    private float yearHour;

    public Statistics(Employee employee, float monthHour, float yearHour) {
        this.employee = employee;
        this.monthHour = monthHour;
        this.yearHour = yearHour;
    }

    // Getters and Setters
    public Employee getEmployee() { return employee; }
    public float getMonthHour() { return monthHour; }
    public float getYearHour() { return yearHour; }
}
