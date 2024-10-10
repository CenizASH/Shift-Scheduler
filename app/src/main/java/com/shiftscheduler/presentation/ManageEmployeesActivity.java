package com.shiftscheduler.presentation;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.shiftscheduler.R;
import com.shiftscheduler.application.MyApplication;
import com.shiftscheduler.business.EmployeeManager;
import com.shiftscheduler.objects.Employee;

import java.util.List;

public class ManageEmployeesActivity extends AppCompatActivity {
    private ListView employeeList;
    private List<Employee> employeeArrayList;
    private ArrayAdapter<Employee> adapter;
    private EmployeeManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_employees);

        // Enable the Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        manager =  ((MyApplication)getApplication()).getEmployeeManager();

        employeeList = findViewById(R.id.employee_list);

        // Initialize employee list and adapter
        employeeArrayList = manager.getAllEmployees();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, employeeArrayList);
        employeeList.setAdapter(adapter);
        employeeList.setOnItemClickListener((parent, view, position, id) -> {
            Employee employee = employeeArrayList.get(position);
            Intent intent = new Intent(this, EmployeeDetailActivity.class);
            intent.putExtra("employeeId", employee.getId());
            startActivityForResult(intent, 1);
        });

        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> addEmployee());
    }

    private void addEmployee() {
        Intent intent = new Intent(this, EmployeeDetailActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            employeeArrayList.clear();
            employeeArrayList.addAll(manager.getAllEmployees());
            adapter.notifyDataSetChanged();
        }
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
