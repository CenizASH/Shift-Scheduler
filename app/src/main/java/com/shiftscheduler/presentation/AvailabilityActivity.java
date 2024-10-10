package com.shiftscheduler.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.shiftscheduler.R;
import com.shiftscheduler.application.MyApplication;
import com.shiftscheduler.business.AvailabilityManager;
import com.shiftscheduler.business.EmployeeManager;
import com.shiftscheduler.objects.Account;
import com.shiftscheduler.objects.Availability;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AvailabilityActivity extends AppCompatActivity implements AvailabilityDialogFragment.OnDialogDismissListener {

    private final String TAG = "AvailabilityActivity";
    private ListView availabilityList;
    private Button buttonAdd;
    private TextView textViewTitle;
    private AvailabilityManager manager;
    private EmployeeManager employeeManager;
    private String weekDay;
    private String date;
    private List<Availability> availabilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);

        // Enable the Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        date = getIntent().getStringExtra("date");

        // 通过formatter将String date转换为Date对象，然后获取星期几
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try {
            date1 = formatter.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        // 再通过formatter获取星期几
        SimpleDateFormat formatter2 = new SimpleDateFormat("EEEE");
        this.weekDay = formatter2.format(date1);

        manager = ((MyApplication)getApplication()).getAvailabilityManager();
        employeeManager = ((MyApplication)getApplication()).getEmployeeManager();

        loadAvailabilities();

        textViewTitle = findViewById(R.id.title);
        textViewTitle.setText(textViewTitle.getText() + " on " + date + "(" + weekDay + ")");

        buttonAdd = findViewById(R.id.add_availability);
        buttonAdd.setOnClickListener(v -> showAvailabilityDialog(null));
    }

    private void loadAvailabilities() {
        availabilityList = findViewById(R.id.availability_list);
        availabilityList.setOnItemClickListener((parent, view, position, id) -> {
            Availability availability = availabilities.get(position);
            Log.d(TAG, "Selected availability: " + availability);
            showAvailabilityDialog(availability);
        });

        Account loginAccount = ((MyApplication)getApplication()).getLoginAccount();
        if (loginAccount.getType().equals("manager")) {
            // Get all availabilities on the date
            availabilities = manager.getAvailabilitiesByDay(date);
        } else {
            // Get all availabilities on the date for the current employee
            availabilities = manager.getAvailabilitiesByDay(date)
                    .stream().filter(a -> a.getEmployeeId().equals(loginAccount.getId()))
                    .collect(Collectors.toList());
        }
        List<String> availabilityStrings = availabilities.stream()
                .map(a -> employeeManager.getEmployeeById(a.getEmployeeId()).getName() + " available on ["
                        + a.getStartTime() + " - " + a.getEndTime() + "] prefers with: "
                        + String.join(", ", a.getPreferences()) + " ,avoids: "
                        + String.join(", ", a.getAvoids())
                        + (a.isArranged() ? ", Arranged!" : ", Not Arrange!"))
                .collect(Collectors.toList());
        Log.d(TAG, availabilityStrings.toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, availabilityStrings);
        availabilityList.setAdapter(adapter);
    }

    public void showAvailabilityDialog(Availability availability) {
        new AvailabilityDialogFragment(date, weekDay, manager, availability, this).show(getSupportFragmentManager(), "availabilityDialog");
    }


    @Override
    public void onDialogDismiss() {
        loadAvailabilities();
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
