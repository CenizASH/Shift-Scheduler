package com.shiftscheduler.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.shiftscheduler.R;
import com.shiftscheduler.application.MyApplication;
import com.shiftscheduler.business.AccountManager;
import com.shiftscheduler.business.EmployeeManager;
import com.shiftscheduler.objects.Account;

import java.util.Arrays;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private Button registerButton;
    private EditText nameEditText;
    private EditText passwordEditText;
    private Spinner spinnerType;
    private EditText confirmPasswordEditText;
    private EditText codeEditText;

    private AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Enable the Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        registerButton = findViewById(R.id.register_button);
        nameEditText = findViewById(R.id.name);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirm_password);
        spinnerType = findViewById(R.id.spinner_type);
        codeEditText = findViewById(R.id.employee_code);

        List<String> types = Arrays.asList("employee", "manager");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        accountManager = ((MyApplication)getApplication()).getAccountManager();

        registerButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();
//            String type = spinnerType.getSelectedItem().toString();
            String type = "employee";
            String code = codeEditText.getText().toString();
            Log.d(TAG, "onCreate: " + name + " " + password + " " + confirmPassword + " " + type + " " + code);

            if (!validateInputs(name, password, confirmPassword, code)) {
                Log.d(TAG, "onCreate: invalid inputs");
                return;
            }

            EmployeeManager employeeManager = ((MyApplication)getApplication()).getEmployeeManager();
            String employeeId = employeeManager.getEmployeeIdByCode(code);
            if (employeeId == null) {
                Toast.makeText(RegisterActivity.this, "Invalid employee code", Toast.LENGTH_SHORT).show();
                return;
            }
            // create a new account
            Account account = new Account(employeeId, name, password,"", type);
            String result = accountManager.register(account);
            Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_LONG).show();
            if (result.equals(AccountManager.ACCOUNT_CREATED)) {
//                goToMain(account);
                // go back to the login page
                finish();
            }
        });
    }

    private boolean validateInputs(String name, String password, String confirmPassword, String code) {
        if (name.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || code.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

//    private void goToMain(Account account) {
//        finish();
//        // Intent to go to MainActivity Activity
//        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//        intent.putExtra("name", account.getName());
//        intent.putExtra("type",account.getType());
//        startActivity(intent);
//    }

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
