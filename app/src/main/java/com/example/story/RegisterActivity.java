package com.example.story;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


import com.example.story.data.AppDatabase;
import com.example.story.data.User;

import java.util.concurrent.Executors;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnRegister;
    private TextView tvLogin;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);

        db = AppDatabase.getInstance(this);

        btnRegister.setOnClickListener(view -> registerUser());

        tvLogin.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void registerUser() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            User existingUser = db.userDao().getUser(username);
            if (existingUser != null) {
                runOnUiThread(() -> Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show());
            } else {
                User newUser = new User();
                newUser.Username = username;
                newUser.Password = password;
                newUser.Role = false;

                db.userDao().insert(newUser);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                });
            }
        });
    }
}
