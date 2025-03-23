package com.example.story.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.story.R;
import com.example.story.data.AppDatabase;
import com.example.story.data.User;

import java.util.concurrent.Executors;

public class DeleteUsFragment extends Fragment {
    private String username;
    private AppDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delete_us_fragment, container, false);
        database = AppDatabase.getInstance(getContext()); // Lấy database instance

        // Nhận username từ Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            username = bundle.getString("username");
        }

        Button yesButton = view.findViewById(R.id.yes_button);
        Button noButton = view.findViewById(R.id.no_button);

        yesButton.setOnClickListener(v -> {
            if (username != null) {
                deleteUser(username);
            }
        });

        noButton.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }

    private void deleteUser(String username) {
        Executors.newSingleThreadExecutor().execute(() -> {
            User user = database.userDao().getUserByUsername(username);
            if (user != null) {
                database.userDao().deleteUser(user);
            }

            getActivity().runOnUiThread(() -> {
                getActivity().getSupportFragmentManager().popBackStack();
            });
        });
    }
}

