package com.shiftscheduler.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.shiftscheduler.R;

import java.time.LocalDate;

public class CalendarView extends AppCompatActivity {

    private android.widget.CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);

        // Enable the Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new android.widget.CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(android.widget.CalendarView view, int year, int month, int dayOfMonth) {
                String date = year + "-" + (month + 1) + "-" + dayOfMonth;
                LocalDate localDate = LocalDate.of(year, month + 1, dayOfMonth);
                showAvailabilityDialog(localDate.toString());
            }
        });
    }

    public void showAvailabilityDialog(String date) {
        Intent intent = new Intent(CalendarView.this, AvailabilityActivity.class);
        intent.putExtra("date", date);
        startActivity(intent);
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
