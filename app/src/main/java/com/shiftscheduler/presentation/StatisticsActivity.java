package com.shiftscheduler.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.shiftscheduler.R;
import com.shiftscheduler.application.MyApplication;
import com.shiftscheduler.business.EmployeeManager;
import com.shiftscheduler.business.StatisticsManager;
import com.shiftscheduler.objects.Account;
import com.shiftscheduler.objects.Statistics;

import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    private StatisticsManager statisticsManager;
    private EmployeeManager employeeManager;
    private List<Statistics> statisticsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        // Enable the Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        statisticsManager = ((MyApplication)getApplication()).getStatisticManager();
        employeeManager = ((MyApplication)getApplication()).getEmployeeManager();

        Account loginAccount = ((MyApplication)getApplication()).getLoginAccount();
        if (loginAccount.getType().equals("manager")) {
            statisticsList = statisticsManager.statisticsAll();
        } else {
            Statistics statistics = statisticsManager.statisticsByEmployee(employeeManager.getEmployeeById(loginAccount.getId()));
            statisticsList = new ArrayList();
            statisticsList.add(statistics);
        }
        populateWeekView();
    }

    private void populateWeekView() {
        TableLayout table = findViewById(R.id.statistics_table);

        for (Statistics statistics : statisticsList) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            populateCell(statistics.getEmployee().getCode(), row);
            populateCell(statistics.getEmployee().getDepartment(), row);
            populateCell(statistics.getEmployee().getName(), row);
            populateCell(String.format("%.1f h", statistics.getMonthHour()), row);
            populateCell(String.format("%.1f h", statistics.getYearHour()), row);

            table.addView(row);
        }
    }

    private void populateCell(String text, TableRow row) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setPadding(8, 18, 8, 18);
        tv.setGravity(android.view.Gravity.CENTER);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT, 1));
        tv.setBackground(getDrawable(R.drawable.border));
        row.addView(tv);
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