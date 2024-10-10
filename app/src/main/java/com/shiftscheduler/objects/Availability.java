package com.shiftscheduler.objects;

import java.util.List;
import java.util.Objects;

public class Availability {
    private String id;
    private String employeeId;
    private String date;
    private String weekDay;
    private String startTime;
    private String endTime;
    private List<String> preferences;
    private List<String> avoids;
    private boolean arranged;

    public Availability(String id, String employeeId, String date, String startTime, String endTime, List<String> preferences, List<String> avoids, String weekDay) {
        this.id = id;
        this.employeeId = employeeId;
        this.date = date;
        this.weekDay = weekDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.preferences = preferences;
        this.avoids = avoids;
    }

    // Getters and setters
    public String getId() { return id; }
    public String getEmployeeId() { return employeeId; }
    public String getDate() { return date; }
    public String getWeekDay() { return weekDay; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public List<String> getPreferences() { return preferences; }
    public List<String> getAvoids() { return avoids; }
    public boolean isArranged() { return arranged; }

    public void setId(String id) { this.id = id; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public void setDate(String date) { this.date = date; }
    public void setWeekDay(String weekDay) { this.weekDay = weekDay; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    public void setPreferences(List<String> preferences) { this.preferences = preferences; }
    public void setAvoids(List<String> avoids) { this.avoids = avoids; }
    public void setArranged(boolean arranged) { this.arranged = arranged; }

    @Override
    public String toString() {
        return "Availability{" +
                "id='" + id + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", date='" + date + '\'' +
                ", weekDay='" + weekDay + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", preferences=" + preferences +
                ", avoids=" + avoids +
                ", arranged=" + arranged +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Availability that = (Availability) o;
        return arranged == that.arranged && Objects.equals(id, that.id) && Objects.equals(employeeId, that.employeeId) && Objects.equals(date, that.date) && Objects.equals(weekDay, that.weekDay) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(String.join(",",preferences), String.join(",",that.preferences)) && Objects.equals(String.join(",",avoids), String.join(",",that.avoids));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employeeId, date, weekDay, startTime, endTime, String.join(",",preferences), String.join(",",avoids), arranged);
    }
}
