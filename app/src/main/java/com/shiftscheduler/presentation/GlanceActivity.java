package com.shiftscheduler.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.shiftscheduler.R;
import com.shiftscheduler.application.MyApplication;
import com.shiftscheduler.business.AvailabilityManager;
import com.shiftscheduler.business.EmployeeManager;
import com.shiftscheduler.objects.Availability;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class GlanceActivity extends AppCompatActivity {
    private final String TAG = "GlanceActivity";
    private AvailabilityManager availabilityManager;
    private EmployeeManager employeeManager;
    private List<String> dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glance);

        // Enable the Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        availabilityManager = ((MyApplication)getApplication()).getAvailabilityManager();
        employeeManager = ((MyApplication)getApplication()).getEmployeeManager();

        // 计算today所有星期的星期一是哪天
        // 获取当前日期
        Date today = new Date();
        // 将Date转换为LocalDate
        LocalDate localDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        // 计算当前日期所在周的星期一是哪一天
        LocalDate monday = localDate.with(DayOfWeek.MONDAY);
        LocalDate tuesday = localDate.with(DayOfWeek.TUESDAY);
        LocalDate wednesday = localDate.with(DayOfWeek.WEDNESDAY);
        LocalDate thursday = localDate.with(DayOfWeek.THURSDAY);
        LocalDate friday = localDate.with(DayOfWeek.FRIDAY);
        LocalDate saturday = localDate.with(DayOfWeek.SATURDAY);
        LocalDate sunday = localDate.with(DayOfWeek.SUNDAY);
        dates = Arrays.asList(monday.toString(), tuesday.toString(), wednesday.toString(),
                thursday.toString(), friday.toString(), saturday.toString(), sunday.toString());

        TextView tvTitle = findViewById(R.id.title);
        tvTitle.setText("Week of " + monday.toString());

        Log.d(TAG, "The Monday of the current week: " + monday);

        populateWeekView();
    }

    private void populateWeekView() {
        List<Integer> viewIds = Arrays.asList(R.id.monday, R.id.tuesday, R.id.wednesday, R.id.thursday, R.id.friday, R.id.saturday, R.id.sunday);
        for (int i = 0; i < viewIds.size(); i++) {
            populateWeekDateView(viewIds.get(i), dates.get(i));
        }
    }

    private void populateWeekDateView(int viewId, String date) {
        TextView tvSaturday = findViewById(viewId);
        List<Availability> availabilities = availabilityManager.getAvailabilitiesByDay(date); // Get all availabilities
        // 获取当天的员工排班信息
        List<String> availabilityStrings = availabilities.stream()
                //.filter(a -> a.getWeekDay().equals(weekDay))
                .filter(a -> a.isArranged())
                .map(a -> employeeManager.getEmployeeById(a.getEmployeeId()).getName() +": "+a.getStartTime()+"-" + a.getEndTime())
                .collect(Collectors.toList());
        Log.d(TAG, availabilityStrings.toString());
        tvSaturday.setText(String.join("\n", availabilityStrings));
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
