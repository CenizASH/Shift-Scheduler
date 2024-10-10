package com.shiftscheduler.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shiftscheduler.R;
import com.shiftscheduler.application.MyApplication;
import com.shiftscheduler.business.AccountManager;
import com.shiftscheduler.objects.Account;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private Button loginButton;
    private Button registerButton;
    private EditText nameEditText;
    private EditText passwordEditText;

    private AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);
        nameEditText = findViewById(R.id.name);
        passwordEditText = findViewById(R.id.password);

        accountManager = ((MyApplication)getApplication()).getAccountManager();

        loginButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            // check if the name and password are valid
            Account account = accountManager.login(name, password);
//            Log.d(TAG, "onCreate: " + account);
            if (account != null) {
                // clear the name and password
                nameEditText.setText("");
                passwordEditText.setText("");
                goToMain(account);
            } else {
                // show a toast to tell the user that the name or password is invalid
                Toast.makeText(LoginActivity.this, "Invalid name or password", Toast.LENGTH_SHORT).show();
            }
        });

        registerButton.setOnClickListener(v -> {
            goToRegister();
        });

    }

    private void goToMain(Account account) {
        ((MyApplication)getApplication()).setLoginAccount(account);
        // Intent to go to MainActivity Activity
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void goToRegister() {
        // Intent to go to RegisterActivity Activity
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
