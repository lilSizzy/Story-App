package com.example.story;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


import com.example.story.data.AppDatabase;
import com.example.story.data.User;

import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        db = AppDatabase.getInstance(this);

        btnLogin.setOnClickListener(view -> loginUser());

        tvRegister.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void loginUser() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            User user = db.userDao().getUserByUsername(username);

            runOnUiThread(() -> {
                if (user != null && user.getPassword().equals(password)) {
                    Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                    Intent intent;
                    if (user.isRole()) { // Nếu Role = true → Mở trang Admin
                        intent = new Intent(LoginActivity.this, AdminActivity.class);
                    } else { // Nếu Role = false → Mở trang chính
                        intent = new Intent(LoginActivity.this, MainActivity.class);
                    }
                    startActivity(intent);
                    finish(); // Kết thúc LoginActivity
                } else {
                    Toast.makeText(this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
