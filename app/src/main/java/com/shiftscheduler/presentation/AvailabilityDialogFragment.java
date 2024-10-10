package com.shiftscheduler.presentation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.shiftscheduler.R;
import com.shiftscheduler.application.MyApplication;
import com.shiftscheduler.business.AvailabilityManager;
import com.shiftscheduler.business.EmployeeManager;
import com.shiftscheduler.objects.Account;
import com.shiftscheduler.objects.Availability;
import com.shiftscheduler.objects.Employee;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AvailabilityDialogFragment extends DialogFragment {
    private final String TAG = "AvailabilityDialogFragment";

    private Spinner employeeIdSpinner;
    private EditText preferencesField, avoidsField;
    private TimePicker startTimePicker, endTimePicker;
    private Availability availability;
    private String date;
    private String weekDay;
    private AvailabilityManager manager;
    private EmployeeManager employeeManager;
    private List<String> employeeIds;

    public interface OnDialogDismissListener {
        void onDialogDismiss();
    }

    private OnDialogDismissListener listener;

    public AvailabilityDialogFragment(String date, String weekDay, AvailabilityManager manager, Availability availability, OnDialogDismissListener listener) {
        this.date = date;
        this.weekDay = weekDay;
        this.manager = manager;
        this.listener = listener;
        this.availability = availability;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_availability_dialog, null);
        employeeIdSpinner = view.findViewById(R.id.employee_id_spinner);
        startTimePicker = view.findViewById(R.id.start_time_picker);
        endTimePicker = view.findViewById(R.id.end_time_picker);
        startTimePicker.setIs24HourView(true);
        endTimePicker.setIs24HourView(true);
        preferencesField = view.findViewById(R.id.preferences);
        avoidsField = view.findViewById(R.id.avoids);

        // 假设您有一个包含所有员工ID的列表
        List<Employee> employees = ((MyApplication)getActivity().getApplication()).getEmployeeManager().getAllEmployees();
        List<String> employeeNames;
        Account loginAccount = ((MyApplication)getActivity().getApplication()).getLoginAccount();
        if (loginAccount.getType().equals("manager")) {
            employeeNames = employees.stream().map(Employee::getName).collect(Collectors.toList());
            employeeIds = employees.stream().map(Employee::getId).collect(Collectors.toList());
        } else {
            employeeNames = employees.stream()
                    .filter(employee -> employee.getId().equals(loginAccount.getId()))
                    .map(Employee::getName).collect(Collectors.toList());
            employeeIds = employees.stream()
                    .filter(employee -> employee.getId().equals(loginAccount.getId()))
                    .map(Employee::getId).collect(Collectors.toList());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, employeeNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employeeIdSpinner.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
//        builder.setTitle("Availabilities for " + date);
        // Set the dialog dimensions
        Window window = builder.create().getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT; // Full width
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT; // Wrap content for height
            window.setAttributes(layoutParams);
        }

        Button submitButton = view.findViewById(R.id.submit_button);
        Button deleteButton = view.findViewById(R.id.delete_button);
        Button updateButton = view.findViewById(R.id.edit_button);

        if (availability == null) {
            builder.setTitle("Add Availabilities for " + date + "(" + weekDay +")");
            employeeIdSpinner.setSelection(0);
            submitButton.setOnClickListener(v -> submitAvailability());
            deleteButton.setVisibility(View.GONE);
            updateButton.setVisibility(View.GONE);
        } else {
            // Set the title of the dialog to "Edit Availability"
            builder.setTitle("Edit Availability for " + date + "(" + weekDay + ")");

            submitButton.setVisibility(View.GONE);

            // Set the fields to the availability's values
            employeeIdSpinner.setSelection(employeeIds.indexOf(availability.getEmployeeId()));
            startTimePicker.setHour(Integer.parseInt(availability.getStartTime().split(":")[0]));
            startTimePicker.setMinute(Integer.parseInt(availability.getStartTime().split(":")[1]));
            endTimePicker.setHour(Integer.parseInt(availability.getEndTime().split(":")[0]));
            endTimePicker.setMinute(Integer.parseInt(availability.getEndTime().split(":")[1]));
            preferencesField.setText(String.join(",", availability.getPreferences()));
            avoidsField.setText(String.join(",", availability.getAvoids()));

            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(v -> deleteAvailability());

            updateButton.setVisibility(View.VISIBLE);
            updateButton.setOnClickListener(v -> modifyAvailability());
        }

        return builder.create();
    }

    private void modifyAvailability() {
        if (!validateTime()) {
            return;
        }

        String startTime = getFormattedTime(startTimePicker);

        String endTime = getFormattedTime(endTimePicker);

        availability.setEmployeeId(employeeIds.get(employeeIdSpinner.getSelectedItemPosition()));
        availability.setStartTime(startTime);
        availability.setEndTime(endTime);
        availability.setPreferences(Arrays.asList(preferencesField.getText().toString().split(",")));
        availability.setAvoids(Arrays.asList(avoidsField.getText().toString().split(",")));

        manager.modifyAvailability(availability);
        if (listener != null) {
            listener.onDialogDismiss();  // 3. 在dismiss()方法中，调用onDialogDismiss()方法
        }
        dismiss();
        Toast.makeText(getActivity(), "Availability updated!", Toast.LENGTH_SHORT).show();
    }

    private void deleteAvailability() {
        manager.removeAvailability(availability.getId());
        if (listener != null) {
            listener.onDialogDismiss();  // 3. 在dismiss()方法中，调用onDialogDismiss()方法
        }
        dismiss();
        Toast.makeText(getActivity(), "Availability deleted!", Toast.LENGTH_SHORT).show();
    }

    private void submitAvailability() {
        if (!validateTime()) {
            return;
        }

        String startTime = getFormattedTime(startTimePicker);

        String endTime = getFormattedTime(endTimePicker);

        String preferences = preferencesField.getText().toString();
        String employeeId = employeeIds.get(employeeIdSpinner.getSelectedItemPosition());
        String avoids = avoidsField.getText().toString();
        //将date转换为LocalDate对象
        Availability availability = new Availability(
                String.valueOf(System.currentTimeMillis()), // Generate a unique ID
                employeeId,
                date,
                startTime,
                endTime,
                Arrays.asList(preferences.split(",")), // Split preferences by commas
                Arrays.asList(avoids.split(",")), // Split avoids by commas
                weekDay
        );

        manager.createAvailability(availability);
        if (listener != null) {
            listener.onDialogDismiss();  // 3. 在dismiss()方法中，调用onDialogDismiss()方法
        }
        dismiss();
        Toast.makeText(getActivity(), "Availability submitted!", Toast.LENGTH_SHORT).show();
    }

    /**
     * validate if the start time is before the end time
     * @return false if the start time is before the end time, true otherwise
     */
    private boolean validateTime() {
        if (startTimePicker.getCurrentHour() > endTimePicker.getCurrentHour() ||
                (startTimePicker.getCurrentHour() == endTimePicker.getCurrentHour() &&
                        startTimePicker.getCurrentMinute() >= endTimePicker.getCurrentMinute())) {
            Toast.makeText(getActivity(), "Start time must be before end time", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @NonNull
    private String getFormattedTime(TimePicker startTimePicker) {
        int hour = startTimePicker.getCurrentHour();
        int minute = startTimePicker.getCurrentMinute();
        // convert the hour and minute to a string as "%02d:%02d"
        String time = String.format("%02d:%02d", hour, minute);
        return time;
    }
}
