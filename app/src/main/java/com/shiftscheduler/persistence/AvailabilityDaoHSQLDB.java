package com.shiftscheduler.persistence;

import com.shiftscheduler.objects.Availability;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AvailabilityDaoHSQLDB implements AvailabilityDao {

    @Override
    public Availability getAvailabilityById(String availabilityId) {
        String sql = "SELECT * FROM availabilities WHERE id = ?";
        try (PreparedStatement pst = DatabaseHelper.getConnection().prepareStatement(sql)) {
            pst.setString(1, availabilityId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Availability availability = new Availability(
                            rs.getString("id"),
                            rs.getString("employeeId"),
                            rs.getString("date"),
                            rs.getString("startTime"),
                            rs.getString("endTime"),
                            Arrays.asList(rs.getString("preferences").split(",")),
                            Arrays.asList(rs.getString("avoids").split(",")),
                            rs.getString("weekDay")
                    );
                    availability.setArranged(rs.getBoolean("arranged"));
                    return availability;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Availability> getAllAvailabilities() {
        List<Availability> availabilities = new ArrayList<>();
        String sql = "SELECT * FROM availabilities";
        try (PreparedStatement pst = DatabaseHelper.getConnection().prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Availability availability = new Availability(
                        rs.getString("id"),
                        rs.getString("employeeId"),
                        rs.getString("date"),
                        rs.getString("startTime"),
                        rs.getString("endTime"),
                        Arrays.asList(rs.getString("preferences").split(",")),
                        Arrays.asList(rs.getString("avoids").split(",")),
                        rs.getString("weekDay")
                );
                availability.setArranged(rs.getBoolean("arranged"));
                availabilities.add(availability);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availabilities;
    }

    @Override
    public List<Availability> getAvailabilityByEmployee(String employeeId) {
        List<Availability> result = new ArrayList<>();
        String sql = "SELECT * FROM availabilities WHERE employeeId = ?";
        try (PreparedStatement pst = DatabaseHelper.getConnection().prepareStatement(sql)) {
            pst.setString(1, employeeId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Availability availability = new Availability(
                            rs.getString("id"),
                            rs.getString("employeeId"),
                            rs.getString("date"),
                            rs.getString("startTime"),
                            rs.getString("endTime"),
                            Arrays.asList(rs.getString("preferences").split(",")),
                            Arrays.asList(rs.getString("avoids").split(",")),
                            rs.getString("weekDay")
                    );
                    availability.setArranged(rs.getBoolean("arranged"));
                    result.add(availability);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Availability> getAvailabilityByWeekDay(String weekDay) {
        List<Availability> result = new ArrayList<>();
        String sql = "SELECT * FROM availabilities WHERE weekDay = ?";
        try (PreparedStatement pst = DatabaseHelper.getConnection().prepareStatement(sql)) {
            pst.setString(1, weekDay);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Availability availability = new Availability(
                            rs.getString("id"),
                            rs.getString("employeeId"),
                            rs.getString("date"),
                            rs.getString("startTime"),
                            rs.getString("endTime"),
                            Arrays.asList(rs.getString("preferences").split(",")),
                            Arrays.asList(rs.getString("avoids").split(",")),
                            rs.getString("weekDay")
                    );
                    availability.setArranged(rs.getBoolean("arranged"));
                    result.add(availability);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Availability> getAvailabilityByDay(String day) {
        List<Availability> result = new ArrayList<>();
        String sql = "SELECT * FROM availabilities WHERE date = ?";
        try (PreparedStatement pst = DatabaseHelper.getConnection().prepareStatement(sql)) {
            pst.setDate(1, Date.valueOf(day));
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Availability availability = new Availability(
                            rs.getString("id"),
                            rs.getString("employeeId"),
                            rs.getString("date"),
                            rs.getString("startTime"),
                            rs.getString("endTime"),
                            Arrays.asList(rs.getString("preferences").split(",")),
                            Arrays.asList(rs.getString("avoids").split(",")),
                            rs.getString("weekDay")
                    );
                    availability.setArranged(rs.getBoolean("arranged"));
                    result.add(availability);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void addAvailability(Availability availability) {
        String sql = "INSERT INTO availabilities (id, employeeId, date, weekDay, startTime, endTime, preferences, avoids, arranged) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = DatabaseHelper.getConnection().prepareStatement(sql)) {
            pst.setString(1, availability.getId());
            pst.setString(2, availability.getEmployeeId());
            pst.setString(3, availability.getDate());
            pst.setString(4, availability.getWeekDay());
            pst.setString(5, availability.getStartTime());
            pst.setString(6, availability.getEndTime());
            pst.setString(7, String.join(",", availability.getPreferences()));
            pst.setString(8, String.join(",", availability.getAvoids()));
            pst.setBoolean(9, availability.isArranged());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAvailability(Availability availability) {
        String sql = "UPDATE availabilities SET employeeId=?, date = ?, weekDay = ?, startTime = ?, endTime = ?, preferences = ?, avoids = ?, arranged = ? WHERE id = ?";
        try (PreparedStatement pst = DatabaseHelper.getConnection().prepareStatement(sql)) {
            pst.setString(1, availability.getEmployeeId());
            pst.setString(2, availability.getDate());
            pst.setString(3, availability.getWeekDay());
            pst.setString(4, availability.getStartTime());
            pst.setString(5, availability.getEndTime());
            pst.setString(6, String.join(",", availability.getPreferences()));
            pst.setString(7, String.join(",", availability.getAvoids()));
            pst.setBoolean(8, availability.isArranged());
            pst.setString(9, availability.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAvailability(String availabilityId) {
        String sql = "DELETE FROM availabilities WHERE id = ?";
        try (PreparedStatement pst = DatabaseHelper.getConnection().prepareStatement(sql)) {
            pst.setString(1, availabilityId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAvailabilityByEmployee(String employeeId) {
        String sql = "DELETE FROM availabilities WHERE employeeId = ?";
        try (PreparedStatement pst = DatabaseHelper.getConnection().prepareStatement(sql)) {
            pst.setString(1, employeeId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void arrangeAvailabilitiesForEmployee(String employeeId) {
        String sql = "UPDATE availabilities SET arranged = TRUE WHERE employeeId = ?";
        try (PreparedStatement pst = DatabaseHelper.getConnection().prepareStatement(sql)) {
            pst.setString(1, employeeId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
