package com.shiftscheduler.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shiftscheduler.R;
import com.shiftscheduler.application.MyApplication;
import com.shiftscheduler.objects.Account;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button btn_manage;
    private TextView tv_name;

    private String name;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the name and type from the intent
        Account loginAccount = ((MyApplication)getApplication()).getLoginAccount();
        name = loginAccount.getName();
        type = loginAccount.getType();

        // Set the name to the text view
        tv_name = findViewById(R.id.tv_welcome);
        tv_name.setText("Welcome, " + name + "(" + type + ")!");

        // Initialize buttons and set onClick listeners
        findViewById(R.id.btn_availability).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAvailability();
            }
        });

        findViewById(R.id.btn_glance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGlance();
            }
        });

        btn_manage = findViewById(R.id.btn_manage);
        if (!type.equals("manager")) {
            btn_manage.setVisibility(View.GONE);
        }
        btn_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToManage();
            }
        });

        findViewById(R.id.btn_work_hour).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyApplication)getApplication()).setLoginAccount(null);
                finish();
            }
        });
    }

    private void goToAvailability() {
        // Intent to go to Availability Activity
        Intent intent = new Intent(MainActivity.this, CalendarView.class);
        startActivity(intent);
    }

    private void goToGlance() {
        // Intent to go to Week at a Glance Activity
        Intent intent = new Intent(MainActivity.this, GlanceActivity.class);
        startActivity(intent);
    }

    private void goToManage() {
        // Intent to go to Manage Employees Activity
        Intent intent = new Intent(MainActivity.this, ManageEmployeesActivity.class);
        startActivity(intent);
    }
}
