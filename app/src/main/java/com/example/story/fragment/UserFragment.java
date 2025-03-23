package com.example.story.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.story.R;
import com.example.story.adapter.UserAdapter;
import com.example.story.data.AppDatabase;
import com.example.story.data.User;

import java.util.List;
import java.util.concurrent.Executors;

public class UserFragment extends Fragment implements UserAdapter.OnUserActionListener {
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private ImageButton addButton;
    private AppDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, container, false);
        recyclerView = view.findViewById(R.id.user_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        addButton = view.findViewById(R.id.add_button);
        database = AppDatabase.getInstance(getContext()); // Lấy database instance

        addButton.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AddUsFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUsers();
    }

    private void loadUsers() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<User> userList = database.userDao().getAllUsers();

            getActivity().runOnUiThread(() -> {
                adapter = new UserAdapter(userList, UserFragment.this);
                recyclerView.setAdapter(adapter);
            });
        });
    }

    @Override
    public void onUpdate(User user) {
        UpdateUsFragment updateUsFragment = new UpdateUsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("username", user.getUsername()); // Truyền username thay vì Parcelable
        updateUsFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, updateUsFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDelete(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.delete_us_fragment, null);
        builder.setView(view);

        // Ánh xạ các Button từ layout
        Button yesButton = view.findViewById(R.id.yes_button);
        Button noButton = view.findViewById(R.id.no_button);

        // Tạo AlertDialog
        AlertDialog dialog = builder.create();

        // Xử lý sự kiện khi bấm "Yes"
        yesButton.setOnClickListener(v -> {
            Executors.newSingleThreadExecutor().execute(() -> {
                database.userDao().deleteUser(user);

                getActivity().runOnUiThread(() -> {
                    loadUsers(); // Refresh danh sách user sau khi xóa
                    Toast.makeText(getContext(), "Đã xóa tài khoản", Toast.LENGTH_SHORT).show();
                });
            });

            dialog.dismiss(); // Đóng hộp thoại sau khi xóa
        });
        noButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}
