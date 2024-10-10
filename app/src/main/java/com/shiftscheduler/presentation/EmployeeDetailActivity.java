package com.shiftscheduler.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.shiftscheduler.R;
import com.shiftscheduler.application.MyApplication;
import com.shiftscheduler.application.NotificationReceiver;
import com.shiftscheduler.business.AvailabilityManager;
import com.shiftscheduler.business.EmployeeManager;
import com.shiftscheduler.objects.Availability;
import com.shiftscheduler.objects.Employee;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeDetailActivity extends AppCompatActivity {

    private EditText nameField, emailField, departmentField;
    private Employee employee;
    private EmployeeManager manager;
    private AvailabilityManager availabilityManager;
    private ListView availabilityList;
    private List<Availability> availabilities;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detail);

        // Enable the Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        manager = ((MyApplication)getApplication()).getEmployeeManager();
        availabilityManager = ((MyApplication)getApplication()).getAvailabilityManager();

        nameField = findViewById(R.id.name);
        emailField = findViewById(R.id.email);
        departmentField = findViewById(R.id.department);

        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> addEmployee());

        Button deleteButton = findViewById(R.id.delete_button);
        Button updateButton = findViewById(R.id.edit_button);

        String employeeId = getIntent().getStringExtra("employeeId");
        if (employeeId == null) {
            deleteButton.setVisibility(View.GONE);
            updateButton.setVisibility(View.GONE);
        } else {
            addButton.setVisibility(View.GONE);

            employee = manager.getEmployeeById(employeeId);
            nameField.setText(employee.getName());
            emailField.setText(employee.getEmail());
            departmentField.setText(employee.getDepartment());

            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(v -> deleteEmployee());

            updateButton.setVisibility(View.VISIBLE);
            updateButton.setOnClickListener(v -> modifyEmployee());

            loadAvailabilities();
        }
    }

    private void addEmployee() {
        String name = nameField.getText().toString();
        String email = emailField.getText().toString();
        String department = departmentField.getText().toString();

        if (!validateInputs(name, email, department)) {
            return;
        }

        Employee employee = new Employee(
                String.valueOf(System.currentTimeMillis()), // Generate unique ID
                name, email, department, Employee.generateCode()
        );
        String result = manager.addEmployee(employee);
        if (result != EmployeeManager.EMPLOYEE_CREATED) {
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    private boolean validateInputs(String name, String email, String department) {
        if (name.isEmpty() || email.isEmpty() || department.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Check if email is valid
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void modifyEmployee() {
        String name = nameField.getText().toString();
        String email = emailField.getText().toString();
        String department = departmentField.getText().toString();

        if (!validateInputs(name, email, department)) {
            return;
        }

        employee.setName(name);
        employee.setEmail(email);
        employee.setDepartment(department);

        manager.updateEmployee(employee);

        Toast.makeText(this, "Employee updated", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    private void deleteEmployee() {
        availabilityManager.removeAvailabilityByEmployee(employee.getId());
        manager.deleteEmployee(employee.getId());
        Toast.makeText(this, "Employee deleted", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    private void loadAvailabilities() {
        availabilityList = findViewById(R.id.availability_list);
        availabilityList.setVisibility(View.VISIBLE);
        availabilities = availabilityManager.getAvailabilitiesForEmployee(employee.getId());
        List<String> availabilityStrings = availabilities.stream()
                //.filter(a -> a.getWeekDay().equals(weekDay))
                .map(a -> manager.getEmployeeById(a.getEmployeeId()).getName() + " available on ["
                        + a.getDate() + " "
                        + a.getStartTime() + " - " + a.getEndTime() + "(" + a.getWeekDay() + ")] prefers with: "
                        + String.join(", ", a.getPreferences())+ " ,avoids: "
                        + String.join(", ", a.getAvoids())
                        + (a.isArranged()?", Arranged!":", Not Arrange!"))
                .collect(Collectors.toList());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, availabilityStrings);
        availabilityList.setAdapter(adapter);

        TextView textViewTitle = findViewById(R.id.section_title);
        textViewTitle.setVisibility(View.VISIBLE);
        textViewTitle.setText("Submitted Availabilities(" + availabilityStrings.size() + ")");

        Button buttonArrange = findViewById(R.id.arrange_button);
        buttonArrange.setVisibility(View.VISIBLE);
        buttonArrange.setOnClickListener(v -> arrangeAvailabilities());
    }

    private void arrangeAvailabilities() {
        availabilityManager.arrangeAvailabilitiesForEmployee(employee.getId());
        Toast.makeText(this, "Availabilities arranged", Toast.LENGTH_SHORT).show();
        loadAvailabilities();

        for (Availability a : availabilities) {
            Log.d("EmployeeDetailActivity", "Availability arranged: " + a.getId());
            String date = a.getDate();
            String startTime = a.getStartTime();
            String title = "Availability arranged";
            String message = "Availability arranged for " + employee.getName() + " on " + a.getDate() + " " + a.getStartTime() + "-" + a.getEndTime();
            int notificationId = (int) Long.parseLong(a.getId());
            
            LocalDateTime dayBefore = calculateMinutesBeforeDateTime(date, startTime, 1440);
            if (dayBefore.isAfter(LocalDateTime.now())) {
                long triggerAtMillis = dayBefore.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                scheduleNotification(title, message, triggerAtMillis, notificationId+1440);
            }
            
            LocalDateTime hourBefore = calculateMinutesBeforeDateTime(date, startTime, 60);
            if (hourBefore.isAfter(LocalDateTime.now())) {
                long triggerAtMillis = hourBefore.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                scheduleNotification(title, message, triggerAtMillis, notificationId+60);
            }
            
            LocalDateTime halfHourBefore = calculateMinutesBeforeDateTime(date, startTime, 30);
            if (halfHourBefore.isAfter(LocalDateTime.now())) {
                long triggerAtMillis = halfHourBefore.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                scheduleNotification(title, message, triggerAtMillis, notificationId+30);
            }
//            else {
//               
//                Log.d("EmployeeDetailActivity", "notification less than half hour: " + halfHourBefore.toString());
//                scheduleNotification(title, message, 0, notificationId);
//            }
        }
    }

    /**
     * Calculate the time n minutes before the given date and time
     * @param date
     * @param time
     * @param minutes the number of minutes to subtract(half hour:30, one hour:60, one day:1440)
     * @return
     */
    private LocalDateTime calculateMinutesBeforeDateTime(String date, String time, int minutes) {
        // Define the date and time format
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        // Parse the date and time strings
        LocalDateTime dateTime = LocalDateTime.parse(date + "T" + time);

        // Subtract 30 minutes
        LocalDateTime newDateTime = dateTime.minusMinutes(minutes);

        return newDateTime;
    }

    @SuppressLint("ScheduleExactAlarm")
    private void scheduleNotification(String title, String message, long triggerAtMillis, int notificationId) {
//        int notificationId = (int)System.currentTimeMillis();
        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("employeeId", "testing");
        intent.putExtra("title", title);
        intent.putExtra("message", message);
        intent.putExtra("notificationId", notificationId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        long triggerAtMillis = System.currentTimeMillis() + delay;
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
