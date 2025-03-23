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

public class AddUsFragment extends Fragment {
    private EditText usernameEditText, passwordEditText;
    private Button saveButton, cancelButton;
    private AppDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_u_fragment, container, false);
        database = AppDatabase.getInstance(getContext()); // Lấy database instance

        usernameEditText = view.findViewById(R.id.username_edit_text);
        passwordEditText = view.findViewById(R.id.password_edit_text);
        saveButton = view.findViewById(R.id.save_button);
        cancelButton = view.findViewById(R.id.cancel_button);

        saveButton.setOnClickListener(v -> addUser());
        cancelButton.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());

        return view;
    }

    private void addUser() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole(false); // Mặc định user không phải admin

        Executors.newSingleThreadExecutor().execute(() -> {
            database.userDao().insertUser(newUser);

            getActivity().runOnUiThread(() -> {
                Toast.makeText(getContext(), "Thêm tài khoản thành công!", Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack();
            });
        });
    }
}

