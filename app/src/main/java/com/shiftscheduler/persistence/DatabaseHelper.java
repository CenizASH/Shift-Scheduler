package com.shiftscheduler.persistence;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper {

    private static final String DB_PATH = "HSQLDB";
    private static final String DB_NAME = "shiftscheduler";
    private static String DB_URL;
    private static final String USER = "SA";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    public static void setupDb(Context context) {
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        DB_URL = "jdbc:hsqldb:file:" + dataDirectory +"/" + DB_NAME +";shutdown=true"; // Use 'file' for persistent storage
        Log.d("DATABASE File URL", DB_URL);

        try (Connection con = getConnection();
             Statement st = con.createStatement()) {
            // Create tables and insert initial data
            st.executeUpdate("CREATE TABLE IF NOT EXISTS employees (id VARCHAR(255) PRIMARY KEY, name VARCHAR(255), email VARCHAR(255), department VARCHAR(255), code VARCHAR(255))");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS accounts (id VARCHAR(255) PRIMARY KEY, name VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, email VARCHAR(255), type VARCHAR(255))");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS Availabilities (id VARCHAR(255) PRIMARY KEY,employeeId VARCHAR(255) NOT NULL, date VARCHAR(255) NOT NULL, weekDay VARCHAR(10), startTime VARCHAR(255) NOT NULL, endTime VARCHAR(255) NOT NULL,preferences VARCHAR(1024), avoids VARCHAR(1024),arranged BOOLEAN NOT NULL)");
            // Check if initial data is needed
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM employees");
            if (rs.next() && rs.getInt(1) == 0) {
                // Database is empty, insert initial data
                insertInitialEmployeeData(con);
            }
            // Add more table creation statements as needed
            rs = st.executeQuery("SELECT COUNT(*) FROM accounts");
            if (rs.next() && rs.getInt(1) == 0) {
                // Database is empty, insert initial data
                insertInitialAccountData(con);
            }
            rs = st.executeQuery("SELECT COUNT(*) FROM availabilities");
            if (rs.next() && rs.getInt(1) == 0) {
                // Database is empty, insert initial data
                insertInitialAvailabilityData(con);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertInitialAccountData(Connection con) {
        String[] accounts = {
                "INSERT INTO accounts (id, name, password, email, type) VALUES ('1', 'manager', 'manager', 'test@test.com', 'manager')",
                "INSERT INTO accounts (id, name, password, email, type) VALUES ('2', 'employee', 'employee', 'test@test.com', 'employee')"
        };
        executeInsertSQLs(con, accounts);
    }

    private static void insertInitialEmployeeData(Connection conn) {
        String[] employees = {
                 "INSERT INTO employees (id, name, email, department, code) VALUES ('1', 'Alice', 'alice@test.com', 'developer', '1A3B5C')",
                 "INSERT INTO employees (id, name, email, department, code) VALUES ('2', 'Bob', 'bob@test.com', 'developer', '2D4F6H')",
                 "INSERT INTO employees (id, name, email, department, code) VALUES ('3', 'Charlie', 'charlie@test.com', 'developer', '3G5I7K')",
                 "INSERT INTO employees (id, name, email, department, code) VALUES ('4', 'David', 'david@test.com', 'developer', '4J6L8M')",
                 "INSERT INTO employees (id, name, email, department, code) VALUES ('5', 'Eve', 'eve@test.com', 'developer', '5N7P9Q')"
        };
        executeInsertSQLs(conn, employees);
    }

    private static void insertInitialAvailabilityData(Connection conn) {
        String[] availabilitySqls = generateAvailabilitySqlStatements();
        executeInsertSQLs(conn, availabilitySqls);
    }

    private static void executeInsertSQLs(Connection conn, String[] sqls) {
        try (Statement stmt = conn.createStatement()) {
            for (String sql : sqls) {
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String[] generateAvailabilitySqlStatements() {
        // Calculate the current date and the dates for the current week's Monday to Sunday
        Date today = new Date();
        LocalDate localDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate monday = localDate.with(DayOfWeek.MONDAY);
        LocalDate tuesday = localDate.with(DayOfWeek.TUESDAY);
        LocalDate wednesday = localDate.with(DayOfWeek.WEDNESDAY);
        LocalDate thursday = localDate.with(DayOfWeek.THURSDAY);
        LocalDate friday = localDate.with(DayOfWeek.FRIDAY);
        LocalDate sunday = localDate.with(DayOfWeek.SUNDAY);

        List<String> statements = new ArrayList<>();
        statements.add(generateInsertStatement("1", "1", monday, "08:00", "16:00", "Monday"));
        statements.add(generateInsertStatement("2", "2", tuesday, "08:00", "16:00", "Tuesday"));
        statements.add(generateInsertStatement("3", "3", wednesday, "08:00", "16:00", "Wednesday"));
        statements.add(generateInsertStatement("4", "4", thursday, "08:00", "16:00", "Thursday"));
        statements.add(generateInsertStatement("5", "5", friday, "20:00", "22:00", "Friday"));
        statements.add(generateInsertStatement("6", "5", sunday, "16:00", "18:00", "Sunday"));

        return statements.toArray(new String[0]);
    }

    private static String generateInsertStatement(String id, String employeeId, LocalDate date, String startTime, String endTime, String weekDay) {
        String sql = String.format(
                "INSERT INTO availabilities (id, employeeId, date, startTime, endTime, preferences, avoids, weekDay, arranged) VALUES ('%s', '%s', '%s', '%s', '%s', '', '', '%s', FALSE);",
                id, employeeId, date.toString(), startTime, endTime, weekDay
        );
        Log.d("SQL", sql);
        return sql;
    }

    public static void dropTables() {
        try (Connection con = getConnection();
             Statement st = con.createStatement()) {
            st.executeUpdate("DROP TABLE IF EXISTS employees");
            st.executeUpdate("DROP TABLE IF EXISTS accounts");
            st.executeUpdate("DROP TABLE IF EXISTS availabilities");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setDbUrl(String dbUrl) {
        DB_URL = "jdbc:hsqldb:file:" + dbUrl +";shutdown=true";
    }
}
