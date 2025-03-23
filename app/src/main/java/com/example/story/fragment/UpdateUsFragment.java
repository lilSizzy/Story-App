package com.example.story.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.story.R;
import com.example.story.data.AppDatabase;
import com.example.story.data.User;

import java.util.concurrent.Executors;

public class UpdateUsFragment extends Fragment {
    private EditText usernameEditText, passwordEditText;
    private Button saveButton, cancelButton;
    private AppDatabase database;
    private String username; // Chỉ truyền username để lấy dữ liệu từ database

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_u_fragment, container, false);
        database = AppDatabase.getInstance(getContext()); // Lấy instance database

        // Nhận username từ Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            username = bundle.getString("username");
        }

        usernameEditText = view.findViewById(R.id.name_edit_text);
        passwordEditText = view.findViewById(R.id.password_edit_text);
        saveButton = view.findViewById(R.id.save_button);
        cancelButton = view.findViewById(R.id.cancel_button);

        loadUserData(); // Gọi hàm load dữ liệu từ DB

        saveButton.setOnClickListener(v -> updateUserData());
        cancelButton.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());

        return view;
    }

    private void loadUserData() {
        if (username == null) return;

        Executors.newSingleThreadExecutor().execute(() -> {
            User user = database.userDao().getUserByUsername(username);
            if (user != null) {
                getActivity().runOnUiThread(() -> {
                    usernameEditText.setText(user.getUsername());
                    passwordEditText.setText(user.getPassword());
                });
            }
        });
    }

    private void updateUserData() {
        String newUsername = usernameEditText.getText().toString().trim();
        String newPassword = passwordEditText.getText().toString().trim();

        if (newUsername.isEmpty() || newPassword.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            User user = database.userDao().getUserByUsername(username);
            if (user != null) {
                user.setUsername(newUsername);
                user.setPassword(newPassword);
                database.userDao().updateUser(user);

                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().popBackStack();
                });
            }
        });
    }
}


